package io.github.architectplatform.engine.core.project.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.architectplatform.api.components.execution.CommandExecutor
import io.github.architectplatform.api.components.execution.ResourceExtractor
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.engine.core.project.app.repositories.ProjectRepository
import io.github.architectplatform.engine.core.project.domain.Project
import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.Path

@Singleton
class ProjectService(
	private val projectRepository: ProjectRepository,
	private val taskRegistry: TaskRegistry,
	private val configLoader: ConfigLoader,
	private val pluginLoader: io.github.architectplatform.engine.core.plugin.app.PluginLoader,
	commandExecutor: CommandExecutor,
	resourceExtractor: ResourceExtractor,
) {

	@Property(name = "architect.engine.core.project.cache.enabled", defaultValue = "true")
	var cacheEnabled: Boolean = true

	private val servicesMap = ConcurrentHashMap<Class<*>, Any>()

	init {
		servicesMap[CommandExecutor::class.java] = commandExecutor
		servicesMap[ResourceExtractor::class.java] = resourceExtractor
	}

	private val objectMapper = ObjectMapper().registerKotlinModule().apply {
		// Configure the ObjectMapper as needed
		// For example, you can enable/disable features, set visibility, etc.
		// Ignore unkown properties globally
		disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
	}

	private fun loadProject(name: String, path: String): Project? {
		val config = configLoader.load(path) ?: return null
		val context = InMemoryProjectContext(Path(path), config, servicesMap)
		val plugins = pluginLoader.load(context)
		plugins.forEach {
			println("Registering plugin ${it.id} for project $name")
			try {
				val rawContext = if (it.contextKey.isEmpty()) {
					// If contextKey is empty, use the plugin's context directly
					config
				} else
				// Otherwise, get the context from the config using the contextKey
					config[it.contextKey]
						?: run {
							println("Context key ${it.contextKey} not found in config, using default context")
							it.context
						}

				require(rawContext != null) {
					"Context for plugin ${it.id} is null, check your config file"
				}

				println("Raw context for plugin ${it.id}: $rawContext (${rawContext::class.qualifiedName})")

				val pluginContext: Any = when (rawContext) {
					is List<*> -> {
						// Config contains a list, so we deserialize as List<ctxClass>
						rawContext.map { item ->
							objectMapper.convertValue(item, it.ctxClass)
						}
					}

					else -> {
						// Config contains a single object (map), deserialize as ctxClass
						objectMapper.convertValue(rawContext, it.ctxClass)
					}

				} ?: throw IllegalArgumentException(
					"Invalid context format for plugin ${it.id}: " +
							"expected object or list, got ${rawContext::class.qualifiedName}"
				)
				it.init(pluginContext)
			} catch (e: Exception) {
				println("Failed to load context ${it.id} for project $name: ${e.message}")
			}
			it.register(taskRegistry)
		}
		return Project(name, path, context, plugins)
	}

	fun registerProject(name: String, path: String) {
		val project = projectRepository.get(name)
		if (project != null) {
			println("Project $name already registered, skipping...")
			return
		}
		println("Registering project $name...")
		val newProject = loadProject(name, path)
			?: throw IllegalArgumentException("Failed to load project $name from path $path")
		projectRepository.save(name, newProject)
		println("Project $name registered")
	}


	fun getProject(name: String): Project? {
		val project = projectRepository.get(name)
		if (project != null) {
			if (!cacheEnabled) {
				println("Cache is disabled, reloading project $name")
				return loadProject(name, project.path)
			} else {
				println("Project $name loaded from cache")
				return project
			}
		}
		return null
	}

	fun getAllProjects(): List<Project> {
		return projectRepository.getAll()
	}
}