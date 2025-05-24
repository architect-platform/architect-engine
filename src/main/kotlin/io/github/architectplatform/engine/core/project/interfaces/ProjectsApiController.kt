package io.github.architectplatform.engine.core.project.interfaces

import io.github.architectplatform.api.project.Config
import io.github.architectplatform.engine.core.project.app.ProjectService
import io.github.architectplatform.engine.core.project.domain.Project
import io.github.architectplatform.engine.core.project.interfaces.dto.ProjectDTO
import io.github.architectplatform.engine.core.project.interfaces.dto.RegisterProjectRequest
import io.github.architectplatform.engine.core.project.interfaces.dto.toDTO
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

@Controller("/api/projects")
class ProjectsApiController(
	private val projectService: ProjectService,
) {

	@Get
	fun getAll(): List<ProjectDTO> {
		println("Getting all projects")
		return projectService.getAllProjects().map(Project::toDTO).also { println("Projects found: $it") }
	}

	@Post
	fun registerProject(@Body request: RegisterProjectRequest): ProjectDTO {
		println("Registering project: ${request.name} at path: ${request.path}")
		projectService.registerProject(request.name, request.path)
		val project = projectService.getProject(request.name)
			?: throw IllegalStateException("Project not found after registration")
		return project.toDTO().also { println("Project registered: $it") }
	}

	@Get("/{name}")
	fun getProject(@PathVariable name: String): ProjectDTO {
		println("Getting project: $name")
		val project = projectService.getProject(name)!!
		return project.toDTO().also { println("Project found: $it") }
	}


	@Get("/{projectName}/config")
	fun getContext(@PathVariable projectName: String): Config {
		val project = projectService.getProject(projectName)!!
		return project.context.config.also { println("Config found: $it") }
	}

}