package io.github.architectplatform.engine.tasks.build

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.build.BuildTask
import jakarta.inject.Singleton

@Singleton
class BuildCommand(val tasks: List<BuildTask>): Command<BuildCommandResult> {
	override val name: String = "build"

	override fun execute(request: CommandRequest): BuildCommandResult {
		println("Executing build command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Build command completed")
		return BuildCommandResult.Success(results)
	}
}