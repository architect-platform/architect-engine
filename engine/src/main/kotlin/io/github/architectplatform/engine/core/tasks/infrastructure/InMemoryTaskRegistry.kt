package io.github.architectplatform.engine.core.tasks.infrastructure

import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskRegistry
import jakarta.inject.Singleton
import java.util.concurrent.ConcurrentHashMap

/** In-memory registry implementation. */
@Singleton
class InMemoryTaskRegistry : TaskRegistry {
  private val tasks = ConcurrentHashMap<String, Task>()

  override fun add(task: Task) {
    tasks[task.id] = task
  }

  override fun get(id: String): Task? {
    return tasks[id]
  }

  override fun all(): List<Task> = tasks.values.toList()
}
