package io.github.architectplatform.engine.commons.tasks.build

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.build.BuildTaskResult

open class BuildCommandResult(
	success: Boolean,
	output: String,
	open val results: List<BuildTaskResult>,
) : CommandResult(success, output) {
	companion object {
		fun success(
			results: List<BuildTaskResult>,
			output: String = "",
		): BuildCommandResult {
			return BuildCommandResult(true, output, results)
		}

		fun failure(
			results: List<BuildTaskResult>,
			output: String = "",
		): BuildCommandResult {
			return BuildCommandResult(false, output, results)
		}
	}
}