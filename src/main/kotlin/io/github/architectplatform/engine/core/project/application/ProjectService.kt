package io.github.architectplatform.engine.core.project.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.architectplatform.engine.core.command.application.CommandLoader
import io.github.architectplatform.engine.core.context.application.ContextLoader
import io.github.architectplatform.engine.core.plugin.PluginLoader
import io.github.architectplatform.engine.core.project.application.domain.Project
import io.github.architectplatform.engine.core.project.application.repositories.ProjectRepository
import jakarta.inject.Singleton

@Singleton
class ProjectService(
	private val projectRepository: ProjectRepository,
	private val contextLoader: ContextLoader,
	private val commandLoader: CommandLoader,
	private val pluginLoader: PluginLoader,
) {

	private val objectMapper = ObjectMapper().registerKotlinModule()

	fun loadProject(project: Project) {
		println("Loading project ${project.name}...")
		project.context = contextLoader.getContext(project.path)
		project.commands = commandLoader.getCommands()
		val definition = project.context["project"] ?: throw IllegalArgumentException("Project definition not found in context")
		val projectDefinition: ProjectDefinition = objectMapper.convertValue(definition)
		project.plugins = pluginLoader.load(projectDefinition.plugins)
		projectRepository.register(project.name, project)
	}

	fun getProject(name: String): Project? {
		return projectRepository.get(name)
	}

	fun getAllProjects(): List<Project> {
		return projectRepository.getAll()
	}
}