package io.github.architectplatform.engine.core.project.interfaces.dto

import io.github.architectplatform.api.command.Command
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ApiCommandDTO(
	val name: String,
	val description: String,
	val usage: String,
	val subcommands: List<ApiCommandDTO> = emptyList()
)

fun Command<*>.toApiDTO(): ApiCommandDTO {
	return ApiCommandDTO(
		name = name,
		description = description,
		usage = usage,
		subcommands = emptyList()
	)
}