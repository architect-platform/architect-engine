package io.github.architectplatform.engine.core.project.interfaces

import io.github.architectplatform.api.core.project.Config
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
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import org.slf4j.LoggerFactory

@Controller("/api/projects")
@ExecuteOn(TaskExecutors.IO)
class ProjectsApiController(
    private val projectService: ProjectService,
) {

  private val logger = LoggerFactory.getLogger(this::class.java)

  @Get
  fun getAll(): List<ProjectDTO> {
    logger.info("Fetching all projects")
    return projectService.getAllProjects().map(Project::toDTO).also {
      logger.debug("Projects found: $it")
    }
  }

  @Post
  fun registerProject(@Body request: RegisterProjectRequest): ProjectDTO {
    logger.info("Registering project: ${request.name} at path: ${request.path}")
    projectService.registerProject(request.name, request.path)
    val project =
        projectService.getProject(request.name)
            ?: throw IllegalStateException("Project not found after registration")
    return project.toDTO()
  }

  @Get("/{name}")
  fun getProject(@PathVariable name: String): ProjectDTO {
    logger.info("Fetching project: $name")
    val project = projectService.getProject(name)!!
    return project.toDTO()
  }

  @Get("/{projectName}/config")
  fun getContext(@PathVariable projectName: String): Config {
    logger.info("Fetching context for project: $projectName")
    val project = projectService.getProject(projectName)!!
    return project.context.config
  }
}
