package io.github.architectplatform.engine.commons.tasks.release

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.release.ReleaseTask
import jakarta.inject.Singleton

@Singleton
class ReleaseCommand(val tasks: List<ReleaseTask>): Command<ReleaseCommandResult> {
	override val name: String = "release"

	override fun execute(request: CommandRequest): ReleaseCommandResult {
		println("Executing release command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Release command completed")
		return ReleaseCommandResult.Success(results)
	}
}