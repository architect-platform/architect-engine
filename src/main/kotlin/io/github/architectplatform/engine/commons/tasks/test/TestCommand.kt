package io.github.architectplatform.engine.commons.tasks.test

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.test.TestTask
import jakarta.inject.Singleton

@Singleton
class TestCommand(val tasks: List<TestTask>): Command<TestCommandResult> {
	override val name: String = "test"

	override fun execute(request: CommandRequest): TestCommandResult {
		println("Executing test command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Test command completed")
		return TestCommandResult.Success(results)
	}
}