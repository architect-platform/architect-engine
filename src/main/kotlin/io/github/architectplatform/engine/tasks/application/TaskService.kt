package io.github.architectplatform.engine.tasks.application

import io.github.architectplatform.api.tasks.Task
import io.github.architectplatform.api.tasks.TaskRegistry
import jakarta.inject.Singleton

@Singleton
class TaskService(
	private val taskRegistry: TaskRegistry,
) {

	fun getAllTasks(): List<Task> {
		return taskRegistry.all()
	}

	fun getTaskById(taskId: String): Task? {
		println("GetTaskById: $taskId - ${taskRegistry.all()}")
		return taskRegistry.all().firstOrNull { it.id == taskId }
	}

}