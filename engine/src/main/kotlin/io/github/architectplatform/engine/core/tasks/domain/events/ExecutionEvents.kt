package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.engine.core.plugin.domain.events.ArchitectEventDTO
import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.github.architectplatform.engine.domain.events.ExecutionEventType
import io.micronaut.serde.annotation.Serdeable

object ExecutionEvents {

  @Serdeable
  data class ExecutionEventDTO(
      override val executionId: String,
      override val success: Boolean,
      override val executionEventType: ExecutionEventType,
  ) : ExecutionEvent

  fun executionStartedEvent(
      executionId: String,
      success: Boolean = true,
  ): ArchitectEvent<ExecutionEvent> {
    return ArchitectEventDTO(
        id = "execution.started",
        event =
            ExecutionEventDTO(
                executionId = executionId,
                success = success,
                executionEventType = ExecutionEventType.STARTED,
            ))
  }

  fun executionCompletedEvent(
      executionId: String,
      success: Boolean = true,
  ): ArchitectEvent<ExecutionEvent> {
    return ArchitectEventDTO(
        id = "execution.completed",
        event =
            ExecutionEventDTO(
                executionId = executionId,
                success = success,
                executionEventType = ExecutionEventType.COMPLETED,
            ))
  }

  fun executionFailedEvent(
      executionId: String,
      success: Boolean = false,
  ): ArchitectEvent<ExecutionEvent> {
    return ArchitectEventDTO(
        id = "execution.failed",
        event =
            ExecutionEventDTO(
                executionId = executionId,
                success = success,
                executionEventType = ExecutionEventType.FAILED,
            ))
  }
}
