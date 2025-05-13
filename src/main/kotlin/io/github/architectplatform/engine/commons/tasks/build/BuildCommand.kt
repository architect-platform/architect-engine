package io.github.architectplatform.engine.commons.tasks.build

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.build.BuildTask
import io.github.architectplatform.engine.core.command.CommandRegistry
import jakarta.inject.Singleton

@Singleton
class BuildCommand(private val commandRegistry: CommandRegistry) : AbstractCommand<BuildCommandResult>() {
	override val name: String = "build"

	override fun execute(request: CommandRequest): BuildCommandResult {
		println("Executing build command")
		val tasks = commandRegistry.getByType<BuildTask>()
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Build command completed")
		return BuildCommandResult.success(results)
	}
}