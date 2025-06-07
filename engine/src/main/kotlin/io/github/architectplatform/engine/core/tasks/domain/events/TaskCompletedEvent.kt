package io.github.architectplatform.engine.core.tasks.domain.events

import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.engine.domain.events.ArchitectEvent

data class TaskCompletedEvent(
    val taskId: String,
    val result: TaskResult,
    override val message: String = "Task completed",
) : ArchitectEvent
