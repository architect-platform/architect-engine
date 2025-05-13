package io.github.architectplatform.engine.commons.tasks.publish

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.publish.PublishTask
import io.github.architectplatform.engine.core.command.CommandRegistry
import jakarta.inject.Singleton

@Singleton
class PublishCommand(private val commandRegistry: CommandRegistry) : AbstractCommand<PublishCommandResult>() {
	override val name: String = "publish"

	override fun execute(request: CommandRequest): PublishCommandResult {
		println("Executing publish command")
		val tasks = commandRegistry.getByType<PublishTask>()
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Publish command completed")
		return PublishCommandResult.success(results)
	}
}