package io.github.architectplatform.engine.commons.phases.application.prepush

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.phases.prepush.PrePushPhase
import io.github.architectplatform.api.phases.prepush.PrePushPhaseResult
import jakarta.inject.Singleton

@Singleton
class PrePushCommand(val tasks: List<PrePushPhase>) : AbstractCommand<PrePushPhaseResult>() {
	override val name: String = "pre-push"

	override fun execute(request: CommandRequest): PrePushPhaseResult {
		println("Executing pre-push command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("pre-push command completed")
		return PrePushPhaseResult.success("Results: $results")
	}
}

