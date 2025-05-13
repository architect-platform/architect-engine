package io.github.architectplatform.engine.commons.phases.application.commitmsg

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.phases.commitmsg.CommitMsgPhase
import io.github.architectplatform.api.phases.commitmsg.CommitMsgPhaseResult
import io.github.architectplatform.engine.core.command.CommandRegistry
import jakarta.inject.Singleton


@Singleton
class CommitMsgCommand(private val commandRegistry: CommandRegistry) : AbstractCommand<CommitMsgPhaseResult>() {
	override val name: String = "commit-msg"

	override fun execute(request: CommandRequest): CommitMsgPhaseResult {
		println("Executing commit-msg command")
		val commitmsgs = commandRegistry.getByType<CommitMsgPhase>()
		val results = commitmsgs.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("commit-msg command completed")
		return CommitMsgPhaseResult.success("Results: $results")
	}

}

