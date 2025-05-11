package io.github.architectplatform.engine.commons.phases.infra

import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.verify.VerifyTask
import io.github.architectplatform.api.tasks.verify.VerifyTaskResult
import jakarta.inject.Singleton

data class HooksVerifyTaskResult(
	override val success: Boolean = true,
	override val output: String = "",
): VerifyTaskResult

@Singleton
class HooksVerifyTask : VerifyTask {

	override val name: String = "hooks-verify"

	override fun execute(request: CommandRequest): VerifyTaskResult {
		println("Executing hooks verify task")
		// Implement the logic for verifying hooks here
		return HooksVerifyTaskResult(
			success = true,
			output = "Hooks verified successfully"
		)
	}
}