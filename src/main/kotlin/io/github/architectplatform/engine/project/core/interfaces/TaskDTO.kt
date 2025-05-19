package io.github.architectplatform.engine.project.core.interfaces

import io.github.architectplatform.api.tasks.Task
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class TaskDTO(
	val id: String,
)

fun Task.toDTO(): TaskDTO {
	return TaskDTO(
		id = id,
	)
}

