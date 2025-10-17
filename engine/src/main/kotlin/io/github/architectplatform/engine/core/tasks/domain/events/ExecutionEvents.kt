package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.engine.core.plugin.domain.events.ArchitectEventDTO
import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.github.architectplatform.engine.domain.events.ExecutionEventType
import io.micronaut.serde.annotation.Serdeable

object ExecutionEvents {

  @Serdeable
  data class ExecutionEventDTO(
      override val project: String,
      override val executionId: String,
      override val success: Boolean,
      override val executionEventType: ExecutionEventType,
  ) : ExecutionEvent

  fun executionStartedEvent(
      project: String,
      executionId: String,
      success: Boolean = true,
  ): ArchitectEvent<ExecutionEvent> {
    return ArchitectEventDTO(
        id = "execution.started",
        event =
            ExecutionEventDTO(
                project = project,
                executionId = executionId,
                success = success,
                executionEventType = ExecutionEventType.STARTED,
            ))
  }

  fun executionCompletedEvent(
      project: String,
      executionId: String,
      success: Boolean = true,
  ): ArchitectEvent<ExecutionEvent> {
    return ArchitectEventDTO(
        id = "execution.completed",
        event =
            ExecutionEventDTO(
                project = project,
                executionId = executionId,
                success = success,
                executionEventType = ExecutionEventType.COMPLETED,
            ))
  }

  fun executionFailedEvent(
      project: String,
      executionId: String,
      success: Boolean = false,
  ): ArchitectEvent<ExecutionEvent> {
    return ArchitectEventDTO(
        id = "execution.failed",
        event =
            ExecutionEventDTO(
                project = project,
                executionId = executionId,
                success = success,
                executionEventType = ExecutionEventType.FAILED,
            ))
  }
}
