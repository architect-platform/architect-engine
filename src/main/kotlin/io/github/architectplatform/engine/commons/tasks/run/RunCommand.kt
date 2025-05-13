package io.github.architectplatform.engine.commons.tasks.run

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.run.RunTask
import io.github.architectplatform.engine.core.command.CommandRegistry
import jakarta.inject.Singleton

@Singleton
class RunCommand(private val commandRegistry: CommandRegistry) : AbstractCommand<RunCommandResult>() {
	override val name: String = "run"

	override fun execute(request: CommandRequest): RunCommandResult {
		println("Executing run command")
		val tasks = commandRegistry.getByType<RunTask>()
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Run command completed")
		return RunCommandResult.success(results)
	}
}