package io.github.architectplatform.engine.modules.phases.application.prepush

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.phases.prepush.PrePushPhase
import io.github.architectplatform.api.phases.prepush.PrePushPhaseResult
import jakarta.inject.Singleton

@Singleton
class PrePushCommand(val tasks: List<PrePushPhase>): Command<PrePushPhaseResult> {
	override val name: String = "pre-push"

	override fun execute(request: CommandRequest): PrePushPhaseResult {
		println("Executing pre-push command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("pre-push command completed")
		return Success(results)
	}

	data class Success(
		val results: List<PrePushPhaseResult>,
		override val success: Boolean = true,
		override val output: String = "",
	) : PrePushPhaseResult

	data class Failure(
		val results: List<PrePushPhaseResult>,
		override val success: Boolean = false,
		override val output: String = "",
	) : PrePushPhaseResult
}

