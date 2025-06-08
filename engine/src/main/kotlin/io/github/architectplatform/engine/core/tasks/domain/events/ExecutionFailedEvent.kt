package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.github.architectplatform.engine.domain.events.ExecutionEventType
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ExecutionFailedEvent(
    override val executionId: String,
    val reason: String,
    override val message: String = "Execution failed",
    override val success: Boolean = false,
    override val eventType: ExecutionEventType = ExecutionEventType.FAILED,
) : ExecutionEvent
