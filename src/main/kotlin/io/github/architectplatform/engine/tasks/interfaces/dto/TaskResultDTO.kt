package io.github.architectplatform.engine.tasks.interfaces.dto

import io.github.architectplatform.api.tasks.TaskResult
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class TaskResultDTO(
	val success: Boolean,
	val message: String?,
)

fun TaskResult.toDTO(): TaskResultDTO {
	return TaskResultDTO(
		success = success,
		message = message,
	)
}