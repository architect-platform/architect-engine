package io.github.architectplatform.engine.commons.tasks.release

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.release.ReleaseTask
import io.github.architectplatform.engine.core.command.CommandRegistry
import jakarta.inject.Singleton

@Singleton
class ReleaseCommand(private val commandRegistry: CommandRegistry) : AbstractCommand<ReleaseCommandResult>() {
	override val name: String = "release"

	override fun execute(request: CommandRequest): ReleaseCommandResult {
		println("Executing release command")
		val tasks = commandRegistry.getByType<ReleaseTask>()
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Release command completed")
		return ReleaseCommandResult.success(results)
	}
}