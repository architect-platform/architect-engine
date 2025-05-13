package io.github.architectplatform.engine.commons.tasks.verify

import io.github.architectplatform.api.command.AbstractCommand
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.verify.VerifyTask
import io.github.architectplatform.engine.core.command.CommandRegistry
import jakarta.inject.Singleton

@Singleton
class VerifyCommand(private val commandRegistry: CommandRegistry) : AbstractCommand<VerifyCommandResult>() {
	override val name: String = "verify"

	override fun execute(request: CommandRequest): VerifyCommandResult {
		println("Executing verify command")
		val tasks = commandRegistry.getByType<VerifyTask>()
		val results = tasks.map { task ->
			println("Running task: ${task.name}")
			task.execute(request)
		}
		println("Verify command completed")
		return VerifyCommandResult.success(results)
	}
}