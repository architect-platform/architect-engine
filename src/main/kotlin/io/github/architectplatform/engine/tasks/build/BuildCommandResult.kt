package io.github.architectplatform.engine.tasks.build

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.build.BuildTaskResult

interface BuildCommandResult : CommandResult {
	/**
	 * The result of the build command.
	 */
	val results: List<BuildTaskResult>

	data class Success(
		override val results: List<BuildTaskResult>,
		override val success: Boolean = true,
		override val output: String = "",
	) : BuildCommandResult

	data class Failure(
		override val results: List<BuildTaskResult>,
		override val success: Boolean = false,
		override val output: String = "",
	) : BuildCommandResult
}