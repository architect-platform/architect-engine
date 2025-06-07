package io.github.architectplatform.engine.plugins.workflows.events

import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase
import io.github.architectplatform.engine.core.tasks.interfaces.dto.TaskDTO

data class PhaseLoaded(val phase: Phase, val tasks: List<TaskDTO>) : TaskResult {
  override val success: Boolean = true
  override val message: String = "Phase loaded: ${phase.id} with ${tasks.size} tasks"
  override val results: List<TaskResult> = emptyList()
}
