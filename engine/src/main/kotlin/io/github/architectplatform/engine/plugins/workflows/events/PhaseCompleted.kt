package io.github.architectplatform.engine.plugins.workflows.events

import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase

data class PhaseCompleted(val phase: Phase, override val results: List<TaskResult>) : TaskResult {
  override val success: Boolean = results.all { it.success }
  override val message: String = "Phase completed: ${phase.id} with ${results.size} results"
}