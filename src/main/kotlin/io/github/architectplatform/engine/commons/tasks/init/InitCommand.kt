package io.github.architectplatform.engine.commons.tasks.init

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.init.InitTask
import io.github.architectplatform.engine.core.command.CommandRegistry
import jakarta.inject.Singleton

@Singleton
class InitCommand(private val commandRegistry: CommandRegistry) : AbstractCommand<InitCommandResult>() {
	override val name: String = "init"

	override fun execute(request: CommandRequest): InitCommandResult {
		println("Executing init command")
		val tasks = commandRegistry.getByType<InitTask>()
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Init command completed")
		return InitCommandResult.success(results)
	}
}