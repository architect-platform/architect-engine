package io.github.architectplatform.engine.plugins.workflows.events

import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase

data class PhaseFailed(val phase: Phase, override val results: List<TaskResult>) : TaskResult {
  override val success: Boolean = false
  override val message: String = "Phase failed: ${phase.id} with ${results.size} results"
}
