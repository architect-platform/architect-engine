package io.github.architectplatform.engine.core.project.interfaces.dto

import io.github.architectplatform.api.command.Command
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class CommandDTO(
	val name: String,
	val description: String,
	val usage: String,
	val subcommands: List<CommandDTO> = emptyList()
)

fun Command<*>.toDTO(): CommandDTO {
	return CommandDTO(
		name = name,
		description = description,
		usage = usage,
		subcommands = emptyList()
	)
}