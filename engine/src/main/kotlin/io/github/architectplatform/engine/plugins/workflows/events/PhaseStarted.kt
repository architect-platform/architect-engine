package io.github.architectplatform.engine.plugins.workflows.events

import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase

data class PhaseStarted(val phase: Phase) : TaskResult {
  override val success: Boolean = true
  override val message: String = "Phase started: ${phase.id}"
  override val results: List<TaskResult> = emptyList()
}
