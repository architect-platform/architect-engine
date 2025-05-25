package io.github.architectplatform.engine.core.tasks.interfaces.dto

import io.github.architectplatform.api.core.tasks.Task
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

