package io.github.architectplatform.engine.core.project.application.domain

import jakarta.inject.Singleton

@Singleton
class ProjectFactory {
	fun createProject(name: String, path: String, description: String = ""): Project {
		return Project(
			name = name,
			path = path,
			description = description,
			context = emptyMap(),
			commands = emptyMap(),
			plugins = emptyMap()
		)
	}
}