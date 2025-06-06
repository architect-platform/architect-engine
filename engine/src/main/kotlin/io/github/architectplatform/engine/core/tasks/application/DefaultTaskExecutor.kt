package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Environment
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.api.core.tasks.TaskResult
import jakarta.inject.Singleton

@Singleton
class DefaultTaskExecutor(
	private val taskRegistry: TaskRegistry,
	private val environment: Environment,
) : TaskExecutor {

	override fun execute(task: Task, context: ProjectContext, args: List<String>): TaskResult {
		// Resolve the full list of dependencies
		val allTasks = resolveAllTasks(task)
		val executionOrder = topologicalSort(allTasks)

		var lastResult: TaskResult? = null

		for (t in executionOrder) {
			if (TaskCache.isCached(t.id)) {
				println("Skipping '${t.id}' (cached)")
				lastResult = TaskCache.get(t.id)
				continue
			}

			println("Executing '${t.id}'")
			val result = t.execute(environment, context, args)
			TaskCache.store(t.id, result)
			lastResult = result
		}

		return lastResult ?: TaskResult.success()
	}

	private fun resolveAllTasks(root: Task): Map<String, Task> {
		val all = mutableMapOf<String, Task>()
		val visited = mutableSetOf<String>()

		fun visit(t: Task) {
			if (t.id in visited) return
			visited.add(t.id)
			for (depId in t.depends()) {
				val depTask = taskRegistry.get(depId)
					?: throw IllegalArgumentException("Task dependency '$depId' not found")
				visit(depTask)
				all[depId] = depTask
			}
			all[t.id] = t
		}

		visit(root)
		println("Graph of tasks to execute:")
		all.keys.forEach { println(" - $it") }
		println("Total tasks to execute: ${all.size}")
		return all
	}

	private fun topologicalSort(tasks: Map<String, Task>): List<Task> {
		val visited = mutableSetOf<String>()
		val result = mutableListOf<Task>()

		fun dfs(current: Task) {
			if (current.id in visited) return
			visited.add(current.id)
			for (depId in current.depends()) {
				val dep = tasks[depId] ?: throw IllegalStateException("Missing task for id $depId")
				dfs(dep)
			}
			result.add(current)
		}

		for (task in tasks.values) {
			dfs(task)
		}

		return result
	}

}