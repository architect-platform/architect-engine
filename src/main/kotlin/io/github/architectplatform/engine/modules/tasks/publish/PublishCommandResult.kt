package io.github.architectplatform.engine.modules.tasks.publish

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.publish.PublishTaskResult

interface PublishCommandResult : CommandResult {
	/**
	 * The result of the precommit command.
	 */
	val results: List<PublishTaskResult>

	data class Success(
		override val results: List<PublishTaskResult>,
		override val success: Boolean = true,
		override val output: String = "",
	) : PublishCommandResult

	data class Failure(
		override val results: List<PublishTaskResult>,
		override val success: Boolean = false,
		override val output: String = "",
	) : PublishCommandResult
}