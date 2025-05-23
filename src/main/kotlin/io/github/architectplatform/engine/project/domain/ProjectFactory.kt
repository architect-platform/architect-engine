package io.github.architectplatform.engine.project.domain

import io.github.architectplatform.api.project.ProjectContext
import jakarta.inject.Singleton

@Singleton
class ProjectFactory {
	fun createProject(name: String, path: String, projectContext: ProjectContext): Project {
		return Project(
			name = name,
			path = path,
			context = projectContext,
			plugins = emptyList(),
		)
	}
}