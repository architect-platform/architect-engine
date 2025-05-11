package io.github.architectplatform.engine.modules.tasks.test

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.test.TestTaskResult

interface TestCommandResult : CommandResult {
	val results: List<TestTaskResult>

	data class Success(
		override val results: List<TestTaskResult>,
		override val success: Boolean = true,
		override val output: String = "",
	) : TestCommandResult

	data class Failure(
		override val results: List<TestTaskResult>,
		override val success: Boolean = false,
		override val output: String = "",
	) : TestCommandResult
}