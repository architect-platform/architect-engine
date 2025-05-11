package io.github.architectplatform.engine.modules.tasks.run

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.run.RunTask
import jakarta.inject.Singleton

@Singleton
class RunCommand(val tasks: List<RunTask>): Command<RunCommandResult> {
	override val name: String = "run"

	override fun execute(request: CommandRequest): RunCommandResult {
		println("Executing run command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Run command completed")
		return RunCommandResult.Success(results)
	}
}