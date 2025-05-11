package io.github.architectplatform.engine.modules.tasks.run

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.run.RunTaskResult

interface RunCommandResult : CommandResult {
	/**
	 * The result of the precommit command.
	 */
	val results: List<RunTaskResult>

	data class Success(
		override val results: List<RunTaskResult>,
		override val success: Boolean = true,
		override val output: String = "",
	) : RunCommandResult

	data class Failure(
		override val results: List<RunTaskResult>,
		override val success: Boolean = false,
		override val output: String = "",
	) : RunCommandResult
}