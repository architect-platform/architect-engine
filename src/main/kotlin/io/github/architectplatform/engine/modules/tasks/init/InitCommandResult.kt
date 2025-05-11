package io.github.architectplatform.engine.modules.tasks.init

import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.api.tasks.init.InitTaskResult

interface InitCommandResult : CommandResult {
	/**
	 * The result of the precommit command.
	 */
	val results: List<InitTaskResult>

	data class Success(
		override val results: List<InitTaskResult>,
		override val success: Boolean = true,
		override val output: String = "",
	) : InitCommandResult

	data class Failure(
		override val results: List<InitTaskResult>,
		override val success: Boolean = false,
		override val output: String = "",
	) : InitCommandResult
}