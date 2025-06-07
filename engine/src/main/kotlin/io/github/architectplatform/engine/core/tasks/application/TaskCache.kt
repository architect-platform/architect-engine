package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.core.tasks.TaskResult
import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton
import java.util.concurrent.ConcurrentHashMap

@Singleton
class TaskCache {

  @Property(name = "architect.cache.enabled", defaultValue = "false")
  private val cacheEnabled: Boolean = false

  private val cache = ConcurrentHashMap<String, TaskResult>()

  fun isCached(taskId: String): Boolean = cacheEnabled && cache.containsKey(taskId)

  fun get(taskId: String): TaskResult? = if (cacheEnabled) cache[taskId] else null

  fun store(taskId: String, result: TaskResult) {
    if (cacheEnabled) {
      cache[taskId] = result
    }
  }

  fun clear() {
    cache.clear()
  }
}
