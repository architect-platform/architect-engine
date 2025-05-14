package io.github.architectplatform.engine.core.project.interfaces.dto

import io.github.architectplatform.api.command.CommandResult
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class CommandResponseDTO(
	val success: Boolean,
	val output: String,
) {

	companion object {
		fun Success(output: String) = CommandResponseDTO(true, output)
		fun Failure(output: String) = CommandResponseDTO(false, output)
	}

}

fun CommandResult.toDTO(): CommandResponseDTO {
	return CommandResponseDTO(this.success, this.output)
}