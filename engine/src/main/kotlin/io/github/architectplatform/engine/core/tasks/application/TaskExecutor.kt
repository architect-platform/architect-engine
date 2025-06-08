package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Environment
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.engine.core.tasks.domain.events.ExecutionCompletedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.ExecutionFailedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.ExecutionStartedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.TaskCompletedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.TaskFailedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.TaskSkippedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.TaskStartedEvent
import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.ExecutionId
import io.github.architectplatform.engine.domain.events.generateExecutionId
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.io.PrintStream

@Singleton
@ExecuteOn(TaskExecutors.BLOCKING)
class TaskExecutor(
    private val taskRegistry: TaskRegistry,
    private val environment: Environment,
    private val taskCache: TaskCache,
    private val eventPublisher: ApplicationEventPublisher<ArchitectEvent>
) {

  fun execute(task: Task, context: ProjectContext, args: List<String>): ExecutionId {
    val executionId = generateExecutionId()
    CoroutineScope(Dispatchers.IO).launch { syncExecuteTask(executionId, task, context, args) }
    return executionId
  }

  private fun syncExecuteTask(
      executionId: ExecutionId,
      task: Task,
      context: ProjectContext,
      args: List<String>
  ) {
    try {
      eventPublisher.publishEvent(ExecutionStartedEvent(executionId))
      val allTasks = resolveAllTasks(task)
      val executionOrder = topologicalSort(allTasks)
      val results =
          executionOrder
              .map {
                if (taskCache.isCached(it.id)) {
                  eventPublisher.publishEvent(
                      TaskSkippedEvent(
                          executionId, it.id, "Task is cached and will not be executed"))
                  val cachedResult = taskCache.get(it.id)
                  if (cachedResult != null) {
                    eventPublisher.publishEvent(
                        TaskCompletedEvent(executionId, it.id, cachedResult))
                    return@map cachedResult
                  }
                }

                eventPublisher.publishEvent(TaskStartedEvent(executionId, it.id))
                try {
                  val result = it.execute(environment, context, args)
                  eventPublisher.publishEvent(TaskCompletedEvent(executionId, it.id, result))
                  taskCache.store(it.id, result)
                  return@map result
                } catch (e: Exception) {
                  eventPublisher.publishEvent(
                      TaskFailedEvent(executionId, it.id, e.message ?: "Task execution failed"))
                }
              }
              .map { it as TaskResult }
      eventPublisher.publishEvent(
          ExecutionCompletedEvent(
              executionId,
              TaskResult.success("All tasks executed successfully", results),
          ))
    } catch (e: Exception) {
      eventPublisher.publishEvent(
          ExecutionFailedEvent(executionId, e.message ?: "Task execution failed"))
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
