package io.github.architectplatform.engine.commons.phases.infra

import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.tasks.init.InitTask
import io.github.architectplatform.api.tasks.init.InitTaskResult
import jakarta.inject.Singleton

data class HooksInitTaskResult(
	override val success: Boolean = true,
	override val output: String = "",
) : InitTaskResult

@Singleton
class HooksInitTask : InitTask() {
	override val name: String = "hooks"

	override fun execute(request: CommandRequest): InitTaskResult {
		println("Executing hooks init task")
		// Implement the logic for initializing hooks here
		return HooksInitTaskResult(
			success = true,
			output = "Hooks initialized successfully"
		)
	}

}