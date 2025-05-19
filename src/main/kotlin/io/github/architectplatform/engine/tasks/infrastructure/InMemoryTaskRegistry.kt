package io.github.architectplatform.engine.tasks.infrastructure

import io.github.architectplatform.api.tasks.Task
import io.github.architectplatform.api.tasks.TaskRegistry
import jakarta.inject.Singleton
import java.util.concurrent.ConcurrentHashMap

/**
 * In-memory registry implementation.
 */
@Singleton
class InMemoryTaskRegistry : TaskRegistry {
	private val tasks = ConcurrentHashMap<String, Task>()
	override fun add(task: Task) {
		tasks[task.id] = task
	}

	override fun all(): List<Task> = tasks.values.toList()
}

