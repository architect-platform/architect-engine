package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Environment
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.api.core.tasks.TaskResult
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class DefaultTaskExecutor(
    private val taskRegistry: TaskRegistry,
    private val environment: Environment,
) : TaskExecutor {

  override fun execute(task: Task, context: ProjectContext, args: List<String>): Flow<TaskResult> =
      flow {
        val allTasks = resolveAllTasks(task)
        val executionOrder = topologicalSort(allTasks)

        for (t in executionOrder) {
          if (TaskCache.isCached(t.id)) {
            println("Skipping '${t.id}' (cached)")
            val cachedResult = TaskCache.get(t.id)
            if (cachedResult != null) emit(cachedResult)
            continue
          }

          println("Executing '${t.id}'")
          val result = t.execute(environment, context, args)
          TaskCache.store(t.id, result)
          emit(result)
        }
      }

  private fun resolveAllTasks(root: Task): Map<String, Task> {
    val all = mutableMapOf<String, Task>()
    val visited = mutableSetOf<String>()

    fun visit(t: Task) {
      if (!visited.add(t.id)) return
      for (depId in t.depends()) {
        val depTask =
            taskRegistry.get(depId)
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
      if (!visited.add(current.id)) return
      for (depId in current.depends()) {
        val dep = tasks[depId] ?: throw IllegalStateException("Missing task for id $depId")
        dfs(dep)
      }
      result.add(current)
    }

    tasks.values.forEach { dfs(it) }

    return result
  }
}
