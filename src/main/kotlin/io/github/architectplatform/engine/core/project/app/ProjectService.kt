package io.github.architectplatform.engine.core.project.app

import io.github.architectplatform.api.execution.CommandExecutor
import io.github.architectplatform.api.tasks.TaskRegistry
import io.github.architectplatform.engine.core.project.app.repositories.ProjectRepository
import io.github.architectplatform.engine.core.project.domain.Project
import io.github.architectplatform.engine.execution.ResourceExtractor
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

	private fun loadProject(name: String, path: String): Project? {
		val config = configLoader.load(path) ?: return null
		val context = InMemoryProjectContext(Path(path), config, servicesMap)
		val plugins = pluginLoader.load(context)
		plugins.forEach {
			println("Registering plugin ${it.id} for project $name")
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