package io.github.architectplatform.engine.project.core.interfaces.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class RegisterProjectRequest(
	val name: String,
	val path: String,
)