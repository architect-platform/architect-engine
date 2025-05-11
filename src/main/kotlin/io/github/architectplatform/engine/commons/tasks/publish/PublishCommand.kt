package io.github.architectplatform.engine.commons.tasks.publish

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.publish.PublishTask
import jakarta.inject.Singleton

@Singleton
class PublishCommand(val tasks: List<PublishTask>): Command<PublishCommandResult> {
	override val name: String = "publish"

	override fun execute(request: CommandRequest): PublishCommandResult {
		println("Executing publish command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Publish command completed")
		return PublishCommandResult.Success(results)
	}
}