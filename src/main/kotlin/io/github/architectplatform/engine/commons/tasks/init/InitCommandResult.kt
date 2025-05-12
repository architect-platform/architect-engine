package io.github.architectplatform.engine.commons.tasks.init

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.init.InitTaskResult

open class InitCommandResult(
	success: Boolean,
	output: String,
	open val results: List<InitTaskResult>,
) : CommandResult(success, output) {
	companion object {
		fun success(
			results: List<InitTaskResult>,
			output: String = "",
		): InitCommandResult {
			return InitCommandResult(true, output, results)
		}

		fun failure(
			results: List<InitTaskResult>,
			output: String = "",
		): InitCommandResult {
			return InitCommandResult(false, output, results)
		}
	}
}