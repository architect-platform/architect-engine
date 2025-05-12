package io.github.architectplatform.engine.commons.tasks.verify

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.verify.VerifyTaskResult

open class VerifyCommandResult(
	success: Boolean,
	output: String,
	open val results: List<VerifyTaskResult>,
) : CommandResult(success, output) {
	companion object {
		fun success(
			results: List<VerifyTaskResult>,
			output: String = "",
		): VerifyCommandResult {
			return VerifyCommandResult(true, output, results)
		}

		fun failure(
			results: List<VerifyTaskResult>,
			output: String = "",
		): VerifyCommandResult {
			return VerifyCommandResult(false, output, results)
		}
	}
}