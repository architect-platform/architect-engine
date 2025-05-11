package io.github.architectplatform.engine.core.context.application

import io.github.architectplatform.api.context.Context
import io.github.architectplatform.engine.core.project.application.ProjectService
import jakarta.inject.Singleton

@Singleton
class ContextService(
	private val projectContextLoader: ProjectContextLoader,
	private val projectService: ProjectService
) {
	fun getContext(projectName: String): Context {
		val project = projectService.getProject(projectName)
			?: throw IllegalArgumentException("Project $projectName not found")
		return projectContextLoader.getContext(project.path)
	}
}