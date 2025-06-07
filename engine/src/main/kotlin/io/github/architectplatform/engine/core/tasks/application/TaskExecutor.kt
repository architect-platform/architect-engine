package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Environment
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.engine.core.tasks.domain.events.TaskCompletedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.TaskSkippedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.TaskStartedEvent
import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.micronaut.context.event.ApplicationEventPublisher
import jakarta.inject.Singleton
import java.io.OutputStream
import java.io.PrintStream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class TaskExecutor(
    private val taskRegistry: TaskRegistry,
    private val environment: Environment,
    private val taskCache: TaskCache,
    private val eventPublisher: ApplicationEventPublisher<ArchitectEvent>
) {

  fun execute(task: Task, context: ProjectContext, args: List<String>): Flow<TaskResult> =
      withSuppressedStdout {
        flow {
          eventPublisher.publishEvent(TaskStartedEvent(task.id))
          val allTasks = resolveAllTasks(task)
          val executionOrder = topologicalSort(allTasks)

          for (subTask in executionOrder) {
            if (taskCache.isCached(subTask.id)) {
              eventPublisher.publishEvent(TaskSkippedEvent(subTask.id, "Task result is cached"))
              val cachedResult = taskCache.get(subTask.id)
              if (cachedResult != null) emit(cachedResult)
              continue
            }

            eventPublisher.publishEvent(TaskStartedEvent(subTask.id))
            val result = subTask.execute(environment, context, args)
            eventPublisher.publishEvent(TaskCompletedEvent(subTask.id, result = result))
            taskCache.store(subTask.id, result)
            emit(result)
          }
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
    all.keys.forEach { println(" - $it") }
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

  private inline fun <T> withSuppressedStdout(block: () -> T): T {
    val originalOut = System.out
    try {
      System.setOut(
          PrintStream(
              object : OutputStream() {
                override fun write(b: Int) {
                  // do nothing
                }
              }))
      return block()
    } finally {
      System.setOut(originalOut)
    }
  }
}
