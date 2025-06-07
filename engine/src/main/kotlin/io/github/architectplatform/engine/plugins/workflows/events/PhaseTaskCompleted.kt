package io.github.architectplatform.engine.plugins.workflows.events

import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase

data class PhaseTaskCompleted(val task: Task, val phase: Phase, val result: TaskResult) :
    TaskResult {
  override val success: Boolean = result.success
  override val message: String =
      "Task completed: ${task.id} in phase: ${phase.id} with result: ${result.success}"
  override val results: List<TaskResult> = listOf(result)
}
