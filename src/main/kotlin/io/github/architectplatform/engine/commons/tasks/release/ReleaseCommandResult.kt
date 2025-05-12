package io.github.architectplatform.engine.commons.tasks.release

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.release.ReleaseTaskResult

open class ReleaseCommandResult(
	success: Boolean,
	output: String,
	open val results: List<ReleaseTaskResult>,
) : CommandResult(success, output) {
	companion object {
		fun success(
			results: List<ReleaseTaskResult>,
			output: String = "",
		): ReleaseCommandResult {
			return ReleaseCommandResult(true, output, results)
		}

		fun failure(
			results: List<ReleaseTaskResult>,
			output: String = "",
		): ReleaseCommandResult {
			return ReleaseCommandResult(false, output, results)
		}
	}
}