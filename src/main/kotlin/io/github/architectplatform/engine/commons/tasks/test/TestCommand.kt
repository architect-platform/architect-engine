package io.github.architectplatform.engine.commons.tasks.test

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.test.TestTask
import io.github.architectplatform.engine.core.command.CommandRegistry
import jakarta.inject.Singleton

@Singleton
class TestCommand(private val commandRegistry: CommandRegistry) : AbstractCommand<TestCommandResult>() {
	override val name: String = "test"

	override fun execute(request: CommandRequest): TestCommandResult {
		println("Executing test command")
		val tasks = commandRegistry.getByType<TestTask>()
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Test command completed")
		return TestCommandResult.success(results)
	}
}