package io.github.architectplatform.engine.command.interfaces.dto

import io.micronaut.serde.annotation.Serdeable

interface ApiCommandResponse {
	val success: Boolean
	val output: String

	@Serdeable
	data class Success(override val output: String) : ApiCommandResponse {
		override val success: Boolean = true
	}

	@Serdeable
	data class Failure(override val output: String) : ApiCommandResponse {
		override val success: Boolean = false
	}
}
