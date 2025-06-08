package io.github.architectplatform.engine.plugins.workflows.events

import io.github.architectplatform.api.core.tasks.TaskResult

data class PhaseTaskFailed(
	val taskId: String,
	val reason: String,
	override val results: List<TaskResult> = emptyList(),
	override val message: String = "Task failed",
	override val success: Boolean = false
) : TaskResult