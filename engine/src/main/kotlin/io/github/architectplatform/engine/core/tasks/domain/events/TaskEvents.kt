package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.engine.core.plugin.domain.events.ArchitectEventDTO
import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.ExecutionEventType
import io.github.architectplatform.engine.domain.events.ExecutionId
import io.github.architectplatform.engine.domain.events.ExecutionTaskEvent
import io.micronaut.serde.annotation.SerdeImport
import io.micronaut.serde.annotation.Serdeable

@SerdeImport(TaskResult::class)
object TaskEvents {

  @Serdeable
  data class TaskEventDTO(
      override val project: String,
      override val executionId: ExecutionId,
      override val taskId: String,
      override val success: Boolean = true,
      override val executionEventType: ExecutionEventType,
  ) : ExecutionTaskEvent

  fun taskCompletedEvent(
      project: String,
      executionId: ExecutionId,
      taskId: String,
      success: Boolean = true,
  ): ArchitectEvent<TaskEventDTO> {
    return ArchitectEventDTO(
        id = "task.completed",
        event =
            TaskEventDTO(
                project = project,
                executionId = executionId,
                taskId = taskId,
                success = success,
                executionEventType = ExecutionEventType.COMPLETED))
  }

  fun taskFailedEvent(
      project: String,
      executionId: ExecutionId,
      taskId: String,
      success: Boolean = false,
  ): ArchitectEvent<TaskEventDTO> {
    return ArchitectEventDTO(
        id = "task.failed",
        event =
            TaskEventDTO(
                project = project,
                executionId = executionId,
                taskId = taskId,
                success = success,
                executionEventType = ExecutionEventType.FAILED))
  }

  fun taskSkippedEvent(
      project: String,
      executionId: ExecutionId,
      taskId: String,
      success: Boolean = true,
  ): ArchitectEvent<TaskEventDTO> {
    return ArchitectEventDTO(
        id = "task.skipped",
        event =
            TaskEventDTO(
                project = project,
                executionId = executionId,
                taskId = taskId,
                success = success,
                executionEventType = ExecutionEventType.SKIPPED))
  }

  fun taskStartedEvent(
      project: String,
      executionId: ExecutionId,
      taskId: String,
      success: Boolean = true,
  ): ArchitectEvent<TaskEventDTO> {
    return ArchitectEventDTO(
        id = "task.started",
        event =
            TaskEventDTO(
                project = project,
                executionId = executionId,
                taskId = taskId,
                success = success,
                executionEventType = ExecutionEventType.STARTED))
  }
}
