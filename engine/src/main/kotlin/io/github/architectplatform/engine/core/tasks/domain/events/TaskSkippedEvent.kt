package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.engine.domain.events.ArchitectEvent

data class TaskSkippedEvent(
    val taskId: String,
    val reason: String,
    override val message: String = "Task skipped",
) : ArchitectEvent
