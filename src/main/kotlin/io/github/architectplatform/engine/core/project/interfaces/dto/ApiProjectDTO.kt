package io.github.architectplatform.engine.core.project.interfaces.dto

import io.github.architectplatform.engine.core.project.application.domain.Project
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ApiProjectDTO(
	val name: String,
	val path: String,
	val description: String,
)

fun Project.toApiDTO(): ApiProjectDTO {
	return ApiProjectDTO(
		name = name,
		path = path,
		description = description,
	)
}