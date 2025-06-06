package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.core.tasks.TaskResult
import java.util.concurrent.ConcurrentHashMap

// Simple in-memory task result cache
object TaskCache {
	private val cache = ConcurrentHashMap<String, TaskResult>()

	fun isCached(taskId: String): Boolean = cache.containsKey(taskId)

	fun get(taskId: String): TaskResult? = cache[taskId]

	fun store(taskId: String, result: TaskResult) {
		cache[taskId] = result
	}

	fun clear() {
		cache.clear()
	}
}