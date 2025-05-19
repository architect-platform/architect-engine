package io.github.architectplatform.engine.project.core.app

import io.github.architectplatform.api.tasks.TaskRegistry
import io.github.architectplatform.engine.plugin.app.PluginLoader
import io.github.architectplatform.engine.project.context.application.ConfigLoader
import io.github.architectplatform.engine.project.core.app.repositories.ProjectRepository
import io.github.architectplatform.engine.project.core.domain.Project
import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.Path

@Singleton
class ProjectService(
	private val projectRepository: ProjectRepository,
	private val taskRegistry: TaskRegistry,
	private val configLoader: ConfigLoader,
	private val pluginLoader: PluginLoader,
) {

	@Property(name = "architect.engine.project.cache.enabled", defaultValue = "true")
	var cacheEnabled: Boolean = true

	private fun loadProject(name: String, path: String): Project {
		val config = configLoader.load(path)
		val context = InMemoryProjectContext(Path(path), config, ConcurrentHashMap())
		val plugins = pluginLoader.load(context)
		plugins.forEach { it.register(taskRegistry) }
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