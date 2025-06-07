package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ExecutionCompletedEvent(
    override val executionId: String,
    val result: TaskResult,
    override val message: String = "Execution completed",
    override val success: Boolean = true,
) : ExecutionEvent
