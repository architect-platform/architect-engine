package io.github.architectplatform.engine.project.core.interfaces

import io.github.architectplatform.engine.project.core.app.ProjectService
import io.github.architectplatform.engine.project.core.domain.Project
import io.github.architectplatform.engine.project.core.interfaces.dto.ConfigDTO
import io.github.architectplatform.engine.project.core.interfaces.dto.ProjectDTO
import io.github.architectplatform.engine.project.core.interfaces.dto.RegisterProjectRequest
import io.github.architectplatform.engine.project.core.interfaces.dto.toDTO
import io.github.architectplatform.engine.tasks.application.TaskService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post


@Controller("/api/projects")
class ProjectsApiController(
	private val projectService: ProjectService,
	private val taskService: TaskService,
) {

	@Get
	fun getAll(): List<ProjectDTO> {
		println("Getting all projects")
		return projectService.getAllProjects().map(Project::toDTO).also { println("Projects found: $it") }
	}

	@Get("/{name}")
	fun getProject(@PathVariable name: String): ProjectDTO {
		println("Getting project: $name")
		val project = projectService.getProject(name)!!
		return project.toDTO().also { println("Project found: $it") }
	}

	@Post
	fun registerProject(@Body request: RegisterProjectRequest): ProjectDTO {
		println("Registering project: ${request.name} at path: ${request.path}")
		projectService.registerProject(request.name, request.path)
		val project = projectService.getProject(request.name)!!
		return project.toDTO().also { println("Project registered: $it") }
	}

	@Get("/{projectName}/config")
	fun getContext(@PathVariable projectName: String): ConfigDTO {
		val project = projectService.getProject(projectName)!!
		return project.context.config.toDTO().also { println("Context found: $it") }
	}

	@Post("/{projectName}/tasks/{taskName}")
	fun executeTask(
		@PathVariable projectName: String,
		@PathVariable taskName: String,
	): TaskResultDTO {
		println("Executing task: $taskName in project: $projectName")
		val project = projectService.getProject(projectName)!!
		val task = taskService.getTaskById(taskName)!!
		val result = task.execute(project.context)
		return TaskResultDTO(
			success = result.success,
			message = result.message,
		).also { println("Task executed: $it") }
	}

}