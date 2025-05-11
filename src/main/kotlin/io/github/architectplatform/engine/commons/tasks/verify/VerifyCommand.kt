package io.github.architectplatform.engine.commons.tasks.verify

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.verify.VerifyTask
import jakarta.inject.Singleton

@Singleton
class VerifyCommand(val tasks: List<VerifyTask>) : AbstractCommand<VerifyCommandResult>() {
	override val name: String = "verify"

	override fun execute(request: CommandRequest): VerifyCommandResult {
		println("Executing verify command")
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Verify command completed")
		return VerifyCommandResult.Success(results)
	}
}