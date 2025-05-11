package io.github.architectplatform.engine.core.project.interfaces.dto

import io.github.architectplatform.api.command.CommandResult
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ApiCommandResponse(
	val success: Boolean,
	val output: String
) {

	companion object {
		fun Success(output: String) = ApiCommandResponse(true, output)
		fun Failure(output: String) = ApiCommandResponse(false, output)
	}

}

fun CommandResult.toApiResponse(): ApiCommandResponse {
	return ApiCommandResponse(this.success, this.output)
}