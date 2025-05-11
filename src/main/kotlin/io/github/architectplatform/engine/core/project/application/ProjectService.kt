package io.github.architectplatform.engine.core.project.application

import io.github.architectplatform.engine.core.command.application.CommonCommandLoader
import io.github.architectplatform.engine.core.context.application.ContextLoader
import io.github.architectplatform.engine.core.plugin.PluginLoader
import io.github.architectplatform.engine.core.project.application.domain.Project
import io.github.architectplatform.engine.core.project.application.repositories.ProjectRepository
import jakarta.inject.Singleton

@Singleton
class ProjectService(
	private val projectRepository: ProjectRepository,
	private val contextLoader: ContextLoader,
	private val commonCommandLoader: CommonCommandLoader,
	private val pluginLoader: PluginLoader,
) {

	fun loadProject(project: Project) {
		println("Loading project ${project.name}...")
		project.context = contextLoader.getContext(project.path)
		project.commands = commonCommandLoader.getCommands()
		project.plugins = pluginLoader.load(project.context)
		projectRepository.register(project.name, project)
	}

	fun getProject(name: String): Project? {
		return projectRepository.get(name)
	}

	fun getAllProjects(): List<Project> {
		return projectRepository.getAll()
	}
}