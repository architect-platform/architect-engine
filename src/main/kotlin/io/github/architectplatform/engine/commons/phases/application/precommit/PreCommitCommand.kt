package io.github.architectplatform.engine.commons.phases.application.precommit

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.phases.precommit.PreCommitPhase
import io.github.architectplatform.api.phases.precommit.PreCommitPhaseResult
import jakarta.inject.Singleton

@Singleton
class PreCommitCommand(val tasks: List<PreCommitPhase>) : AbstractCommand<PreCommitPhaseResult>() {
	override val name: String = "pre-commit"

	override fun execute(request: CommandRequest): PreCommitPhaseResult {
		println("Executing pre-commit command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("pre-commit command completed")
		return Success(results)
	}

	data class Success(
		val results: List<PreCommitPhaseResult>,
		override val success: Boolean = true,
		override val output: String = "",
	) : PreCommitPhaseResult

	data class Failure(
		val results: List<PreCommitPhaseResult>,
		override val success: Boolean = false,
		override val output: String = "",
	) : PreCommitPhaseResult
}

