package io.github.architectplatform.engine.modules.phases.application.commitmsg

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.phases.commitmsg.CommitMsgPhase
import io.github.architectplatform.api.phases.commitmsg.CommitMsgPhaseResult
import jakarta.inject.Singleton

@Singleton
class CommitMsgCommand(val tasks: List<CommitMsgPhase>): Command<CommitMsgPhaseResult> {
	override val name: String = "commit-msg"

	override fun execute(request: CommandRequest): CommitMsgPhaseResult {
		println("Executing commit-msg command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("commit-msg command completed")
		return Success(results)
	}

	data class Success(
		val results: List<CommitMsgPhaseResult>,
		override val success: Boolean = true,
		override val output: String = "",
	) : CommitMsgPhaseResult

	data class Failure(
		val results: List<CommitMsgPhaseResult>,
		override val success: Boolean = false,
		override val output: String = "",
	) : CommitMsgPhaseResult
}

