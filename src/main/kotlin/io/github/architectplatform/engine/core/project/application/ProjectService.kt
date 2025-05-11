package io.github.architectplatform.engine.core.project.application

import io.github.architectplatform.engine.core.project.application.repositories.ProjectRepository
import io.github.architectplatform.engine.core.project.application.domain.Project
import jakarta.inject.Singleton

@Singleton
class ProjectService(
	private val projectRepository: ProjectRepository
) {
	fun registerProject(project: Project) {
		projectRepository.register(project.name, project)
	}

	fun getProject(name: String): Project? {
		return projectRepository.get(name)
	}

	fun getAllProjects(): List<Project> {
		return projectRepository.getAll()
	}
}