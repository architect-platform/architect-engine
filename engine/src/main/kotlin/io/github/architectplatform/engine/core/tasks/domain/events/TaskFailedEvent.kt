package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.engine.domain.events.ExecutionEventType
import io.github.architectplatform.engine.domain.events.ExecutionTaskEvent
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class TaskFailedEvent(
    override val executionId: String,
    override val taskId: String,
    val reason: String,
    override val message: String = "Task failed",
    override val success: Boolean = false,
    override val eventType: ExecutionEventType = ExecutionEventType.FAILED,
) : ExecutionTaskEvent
