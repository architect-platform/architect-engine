package io.github.architectplatform.engine.commons.tasks.run

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.run.RunTaskResult

open class RunCommandResult(
	success: Boolean,
	output: String,
	open val results: List<RunTaskResult>,
) : CommandResult(success, output) {
	companion object {
		fun success(
			results: List<RunTaskResult>,
			output: String = "",
		): RunCommandResult {
			return RunCommandResult(true, output, results)
		}

		fun failure(
			results: List<RunTaskResult>,
			output: String = "",
		): RunCommandResult {
			return RunCommandResult(false, output, results)
		}
	}
}