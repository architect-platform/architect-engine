package io.github.architectplatform.engine.core.project.application

import io.github.architectplatform.engine.core.command.CommonCommandLoader
import io.github.architectplatform.engine.core.context.application.ContextLoader
import io.github.architectplatform.engine.core.plugin.application.PluginLoader
import io.github.architectplatform.engine.core.project.application.domain.Project
import io.github.architectplatform.engine.core.project.application.repositories.ProjectRepository
import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton

@Singleton
class ProjectService(
	private val projectRepository: ProjectRepository,
	private val contextLoader: ContextLoader,
	private val commonCommandLoader: CommonCommandLoader,
	private val pluginLoader: PluginLoader,
) {

	@Property(name = "architect.engine.project.cache.enabled", defaultValue = "true")
	var cacheEnabled: Boolean = true

	fun loadProject(project: Project) {
		println("Loading project ${project.name}...")
		project.context = contextLoader.getContext(project.path)
		project.commands = commonCommandLoader.getCommands()
		project.plugins = pluginLoader.load(project.context)
		projectRepository.save(project.name, project)
	}

	fun getProject(name: String): Project? {
		if (!cacheEnabled) {
			println("Cache is disabled, reloading project $name")
			val project = projectRepository.get(name)
			project?.let { loadProject(it) }
			return project
		}
		return projectRepository.get(name)
	}

	fun getAllProjects(): List<Project> {
		return projectRepository.getAll()
	}
}