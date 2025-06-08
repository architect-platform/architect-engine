package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.engine.domain.events.ExecutionEventType
import io.github.architectplatform.engine.domain.events.ExecutionTaskEvent
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class TaskStartedEvent(
    override val executionId: String,
    override val taskId: String,
    override val message: String = "Task started",
    override val success: Boolean = true,
    override val eventType: ExecutionEventType = ExecutionEventType.STARTED,
) : ExecutionTaskEvent
