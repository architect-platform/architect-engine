package io.github.architectplatform.engine.core.project.interfaces.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ApiRegisterProjectRequest(
	val name: String,
	val path: String,
)