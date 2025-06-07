package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.engine.domain.events.ArchitectEvent

data class TaskStartedEvent(
    val taskId: String,
    override val message: String = "Task started",
) : ArchitectEvent
