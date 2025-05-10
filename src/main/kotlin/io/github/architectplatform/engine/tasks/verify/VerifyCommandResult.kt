package io.github.architectplatform.engine.tasks.verify

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.verify.VerifyTaskResult

interface VerifyCommandResult : CommandResult {
	/**
	 * The result of the build command.
	 */
	val results: List<VerifyTaskResult>

	data class Success(
		override val results: List<VerifyTaskResult>,
		override val success: Boolean = true,
		override val output: String = "",
	) : VerifyCommandResult

	data class Failure(
		override val results: List<VerifyTaskResult>,
		override val success: Boolean = false,
		override val output: String = "",
	) : VerifyCommandResult
}