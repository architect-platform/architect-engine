package io.github.architectplatform.engine.core.project.interfaces

import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.engine.core.project.application.ProjectService
import io.github.architectplatform.engine.core.project.application.domain.Project
import io.github.architectplatform.engine.core.project.application.domain.ProjectFactory
import io.github.architectplatform.engine.core.project.interfaces.dto.ApiCommandDTO
import io.github.architectplatform.engine.core.project.interfaces.dto.ApiCommandResponse
import io.github.architectplatform.engine.core.project.interfaces.dto.ApiProjectDTO
import io.github.architectplatform.engine.core.project.interfaces.dto.ApiRegisterProjectRequest
import io.github.architectplatform.engine.core.project.interfaces.dto.ContextDTO
import io.github.architectplatform.engine.core.project.interfaces.dto.toApiDTO
import io.github.architectplatform.engine.core.project.interfaces.dto.toApiResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post


@Controller("/api/projects")
class ProjectsApiController(
	private val projectService: ProjectService,
	private val projectFactory: ProjectFactory,
) {

	@Error(global = true)
	fun handleError(exception: Exception): ApiCommandResponse {
		println("Error occurred: ${exception.message}")
		return ApiCommandResponse(
			success = false,
			output = exception.message ?: "Unknown error",
		)
	}

	@Get
	fun getAll(): List<ApiProjectDTO> {
		println("Getting all projects")
		return projectService.getAllProjects().map(Project::toApiDTO).also { println("Projects found: $it") }
	}

	@Get("/{name}")
	fun getProject(@PathVariable name: String): ApiProjectDTO? {
		println("Getting project: $name")
		val project = projectService.getProject(name)
		return project?.toApiDTO().also { println("Project found: $it") }
	}

	@Post
	fun registerProject(@Body request: ApiRegisterProjectRequest): ApiProjectDTO {
		println("Registering project: ${request.name} at path: ${request.path}")
		val project = projectFactory.createProject(request.name, request.path)
		projectService.loadProject(project)
		return project.toApiDTO().also { println("Project registered: $it") }
	}

	@Get("/{projectName}/commands")
	fun getAll(@PathVariable projectName: String): List<ApiCommandDTO> {
		println("Getting all commands for project: $projectName")
		val commands = projectService.getProject(projectName)?.commands
			?: throw IllegalArgumentException("Project not found: $projectName")
		return commands.values.map { it.toApiDTO() }.also { println("Commands found: $it") }
	}

	@Get("/{projectName}/commands/{commandName}")
	fun getCommand(@PathVariable projectName: String, @PathVariable commandName: String): ApiCommandDTO {
		println("Getting command: $commandName for project: $projectName")
		val project = projectService.getProject(projectName)
			?: throw IllegalArgumentException("Project not found: $projectName")
		val command = project.getCommand(commandName)
			?: throw IllegalArgumentException("Command not found: $commandName")
		return command.toApiDTO().also { println("Command found: $it") }
	}

	@Post("/{projectName}/commands/{commandName}")
	fun executeCommand(
		@PathVariable projectName: String,
		@PathVariable commandName: String,
		@Body args: List<String>,
	): ApiCommandResponse {
		println("Executing command: $commandName for project: $projectName with args: $args")
		val project = projectService.getProject(projectName)
			?: throw IllegalArgumentException("Project not found: $projectName")
		val command = project.getCommand(commandName)
			?: throw IllegalArgumentException("Command not found: $commandName")
		val commandRequest = CommandRequest(project.path, args)
		val commandResult = command.execute(commandRequest)
		return commandResult.toApiResponse().also { println("Command result: $it") }
	}

	@Get("/{projectName}/context")
	fun getContext(@PathVariable projectName: String): ContextDTO {
		val project = projectService.getProject(projectName)
			?: throw IllegalArgumentException("Project not found: $projectName")
		return project.context.toApiDTO().also { println("Context found: $it") }
	}

}