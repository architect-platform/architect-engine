package io.github.architectplatform.engine.core.project.interfaces.dto

import io.github.architectplatform.engine.core.project.application.domain.Project
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ProjectDTO(
	val name: String,
	val path: String,
)

fun Project.toDTO(): ProjectDTO {
	return ProjectDTO(
		name = name,
		path = path,
	)
}