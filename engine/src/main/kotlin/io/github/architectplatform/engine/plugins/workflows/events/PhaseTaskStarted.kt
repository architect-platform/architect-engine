package io.github.architectplatform.engine.plugins.workflows.events

import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase

data class PhaseTaskStarted(val task: Task, val phase: Phase) : TaskResult {
  override val success: Boolean = true
  override val message: String = "Task started: ${task.id} in phase: ${phase.id}"
  override val results: List<TaskResult> = emptyList()
}
