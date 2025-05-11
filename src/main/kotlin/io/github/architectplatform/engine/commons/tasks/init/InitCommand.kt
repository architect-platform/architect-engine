package io.github.architectplatform.engine.commons.tasks.init

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.init.InitTask
import jakarta.inject.Singleton

@Singleton
class InitCommand(val tasks: List<InitTask>): Command<InitCommandResult> {
	override val name: String = "init"

	override fun execute(request: CommandRequest): InitCommandResult {
		println("Executing init command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Init command completed")
		return InitCommandResult.Success(results)
	}
}