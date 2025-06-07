package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.engine.domain.events.ExecutionId
import io.github.architectplatform.engine.domain.events.ExecutionTaskEvent
import io.micronaut.serde.annotation.SerdeImport
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class TaskCompletedEvent(
    override val executionId: ExecutionId,
    override val taskId: String,
    val result: TaskResult,
    override val message: String = "Task completed",
    override val success: Boolean = true,
) : ExecutionTaskEvent

@SerdeImport(TaskResult::class)
object TaskCompletedEventSerdeImport {
  // This object is used to import TaskResult for serialization/deserialization
  // in the context of TaskCompletedEvent.
  // It ensures that TaskResult is available for Serde processing.
}
