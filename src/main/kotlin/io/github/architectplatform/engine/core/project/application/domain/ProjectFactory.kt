package io.github.architectplatform.engine.core.project.application.domain

import jakarta.inject.Singleton

@Singleton
class ProjectFactory {
	fun createProject(name: String, path: String, description: String = ""): Project {
		return object : Project {
			override val name: String = name
			override val path: String = path
			override val description: String = description
		}
	}
}