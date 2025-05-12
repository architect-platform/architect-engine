package io.github.architectplatform.engine.commons.tasks.publish

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.publish.PublishTaskResult

open class PublishCommandResult(
	success: Boolean,
	output: String,
	open val results: List<PublishTaskResult>,
) : CommandResult(success, output) {
	companion object {
		fun success(
			results: List<PublishTaskResult>,
			output: String = "",
		): PublishCommandResult {
			return PublishCommandResult(true, output, results)
		}

		fun failure(
			results: List<PublishTaskResult>,
			output: String = "",
		): PublishCommandResult {
			return PublishCommandResult(false, output, results)
		}
	}
}