package io.github.architectplatform.engine.core.project.interfaces.dto

import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.engine.core.project.domain.Project
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ProjectDTO(
	val name: String,
	val path: String,
	val context: ProjectContextDTO,
)

@Serdeable
data class ProjectContextDTO(
	val dir: String,
	val config: Map<String, Any>,
)

fun ProjectContext.toDTO(): ProjectContextDTO {
	return ProjectContextDTO(
		dir = dir.toString(),
		config = config,
	)
}

fun Project.toDTO(): ProjectDTO {
	return ProjectDTO(
		name = name,
		path = path,
		context = context.toDTO(),
	)
}