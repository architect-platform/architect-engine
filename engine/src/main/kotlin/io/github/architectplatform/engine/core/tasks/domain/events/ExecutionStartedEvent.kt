package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.github.architectplatform.engine.domain.events.ExecutionEventType
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ExecutionStartedEvent(
    override val executionId: String,
    override val message: String = "Execution started",
    override val success: Boolean = true,
    override val eventType: ExecutionEventType = ExecutionEventType.STARTED,
) : ExecutionEvent
