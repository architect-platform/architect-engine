package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.project.getKey
import io.github.architectplatform.api.core.tasks.Environment
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.engine.core.project.domain.Project
import io.github.architectplatform.engine.core.tasks.domain.events.ExecutionEvents.executionCompletedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.ExecutionEvents.executionFailedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.ExecutionEvents.executionStartedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.TaskEvents.taskCompletedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.TaskEvents.taskFailedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.TaskEvents.taskSkippedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.TaskEvents.taskStartedEvent
import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.ExecutionId
import io.github.architectplatform.engine.domain.events.generateExecutionId
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Singleton
import java.io.OutputStream
import java.io.PrintStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

@Singleton
@ExecuteOn(TaskExecutors.BLOCKING)
class TaskExecutor(
    private val environment: Environment,
    private val taskCache: TaskCache,
    private val eventPublisher: ApplicationEventPublisher<ArchitectEvent<*>>
) {

  private val logger = LoggerFactory.getLogger(this::class.java)

  fun execute(
      project: Project,
      task: Task,
      context: ProjectContext,
      args: List<String>
  ): ExecutionId {
    val executionId = generateExecutionId()
    CoroutineScope(Dispatchers.IO).launch {
      syncExecuteTask(executionId, task, context, args, project.taskRegistry)
    }
    return executionId
  }

  private fun syncExecuteTask(
      executionId: ExecutionId,
      task: Task,
      projectContext: ProjectContext,
      args: List<String>,
      taskRegistry: TaskRegistry,
  ) {
    val projectName = projectContext.config.getKey<String>("project.name") ?: "unknown"

    try {
      eventPublisher.publishEvent(executionStartedEvent(projectName, executionId))
      val allTasks = resolveAllTasks(task, taskRegistry)
      val executionOrder = topologicalSort(allTasks)
      val results =
          executionOrder
              .map {
                Thread.sleep(500) // Simulate some delay for demonstration purposes
                if (taskCache.isCached(it.id)) {
                  eventPublisher.publishEvent(
                      taskSkippedEvent(
                          projectName,
                          executionId,
                          it.id,
                      ))
                  val cachedResult = taskCache.get(it.id)
                  if (cachedResult != null) {
                    eventPublisher.publishEvent(taskCompletedEvent(projectName, executionId, it.id))
                    return@map cachedResult
                  }
                }

                eventPublisher.publishEvent(taskStartedEvent(projectName, executionId, it.id))
                try {
                  val result = it.execute(environment, projectContext, args)
                  Thread.sleep(500) // Simulate some delay for demonstration purposes
                  if (!result.success) {
                    logger.info(
                        "Exception during execution of task '${it.id}' in project '$projectName': ${result.message}")
                    eventPublisher.publishEvent(
                        taskFailedEvent(
                            projectName,
                            executionId,
                            it.id,
                        ))
                  } else {
                    eventPublisher.publishEvent(taskCompletedEvent(projectName, executionId, it.id))
                  }
                  taskCache.store(it.id, result)
                  return@map result
                } catch (e: Exception) {
                  Thread.sleep(500) // Simulate some delay for demonstration purposes
                  eventPublisher.publishEvent(
                      taskFailedEvent(
                          projectName,
                          executionId,
                          it.id,
                      ))
                  logger.info("Exception during execution of task '${it.id}': ${e.message}")
                  return@map TaskResult.failure(
                      "Task '${it.id}' failed with exception: ${e.message ?: "Unknown error"}")
                }
              }
              .map { it }
      val success = results.all { it.success }
      if (!success) {
        eventPublisher.publishEvent(executionFailedEvent(projectName, executionId))
      } else {
        eventPublisher.publishEvent(executionCompletedEvent(projectName, executionId))
      }
    } catch (e: Exception) {
      eventPublisher.publishEvent(
          executionFailedEvent(
              projectName,
              executionId,
          ))
    }
  }

  private fun resolveAllTasks(root: Task, taskRegistry: TaskRegistry): Map<String, Task> {
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
