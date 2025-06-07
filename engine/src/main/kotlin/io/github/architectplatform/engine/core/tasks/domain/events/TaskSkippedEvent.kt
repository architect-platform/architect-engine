package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.engine.domain.events.ExecutionTaskEvent
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class TaskSkippedEvent(
    override val executionId: String,
    override val taskId: String,
    val reason: String,
    override val message: String = "Task skipped",
    override val success: Boolean = true,
) : ExecutionTaskEvent
