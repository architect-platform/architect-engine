package io.github.architectplatform.engine.commons.phases.application.precommit

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.phases.precommit.PreCommitPhase
import io.github.architectplatform.api.phases.precommit.PreCommitPhaseResult
import io.github.architectplatform.engine.core.command.CommandRegistry
import jakarta.inject.Singleton

@Singleton
class PreCommitCommand(private val commandRegistry: CommandRegistry) : AbstractCommand<PreCommitPhaseResult>() {
	override val name: String = "pre-commit"

	override fun execute(request: CommandRequest): PreCommitPhaseResult {
		println("Executing pre-commit command")
		val precommits = commandRegistry.getByType<PreCommitPhase>()
		val results = precommits.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("pre-commit command completed")
		return PreCommitPhaseResult.success("Results: $results")
	}
}

