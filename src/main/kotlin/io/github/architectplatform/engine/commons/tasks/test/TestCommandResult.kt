package io.github.architectplatform.engine.commons.tasks.test

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.test.TestTaskResult

open class TestCommandResult(
	success: Boolean,
	output: String,
	open val results: List<TestTaskResult>,
) : CommandResult(success, output) {
	companion object {
		fun success(
			results: List<TestTaskResult>,
			output: String = "",
		): TestCommandResult {
			return TestCommandResult(true, output, results)
		}

		fun failure(
			results: List<TestTaskResult>,
			output: String = "",
		): TestCommandResult {
			return TestCommandResult(false, output, results)
		}
	}
}