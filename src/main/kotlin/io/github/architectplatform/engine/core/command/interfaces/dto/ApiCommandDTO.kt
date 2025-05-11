package io.github.architectplatform.engine.core.command.interfaces.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ApiCommandDTO(
	val name: String,
	val description: String,
	val usage: String,
	val subcommands: List<ApiCommandDTO> = emptyList()
)