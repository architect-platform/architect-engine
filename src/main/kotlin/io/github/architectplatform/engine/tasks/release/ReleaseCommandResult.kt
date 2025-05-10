package io.github.architectplatform.engine.tasks.release

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.release.ReleaseTaskResult

interface ReleaseCommandResult : CommandResult {
	/**
	 * The result of the build command.
	 */
	val results: List<ReleaseTaskResult>

	data class Success(
		override val results: List<ReleaseTaskResult>,
		override val success: Boolean = true,
		override val output: String = "",
	) : ReleaseCommandResult

	data class Failure(
		override val results: List<ReleaseTaskResult>,
		override val success: Boolean = false,
		override val output: String = "",
	) : ReleaseCommandResult
}