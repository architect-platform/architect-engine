package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.engine.core.project.app.ProjectService
import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.github.architectplatform.engine.domain.events.ExecutionId
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class TaskService(
    private val taskRegistry: TaskRegistry,
    private val projectService: ProjectService,
    private val executor: TaskExecutor,
    private val eventCollector: ExecutionEventCollector
) {

  fun getAllTasks(): List<Task> {
    return taskRegistry.all().filter { it.phase() == null }.sortedBy { it.id }
  }

  fun getTaskById(taskId: String): Task? {
    println("GetTaskById: $taskId - ${taskRegistry.all()}")
    return taskRegistry.all().firstOrNull { it.id == taskId }
  }

  fun executeTask(taskId: String, projectName: String, args: List<String>): ExecutionId {
    val project =
        projectService.getProject(projectName)
            ?: throw IllegalArgumentException("Project not found")
    val task =
        taskRegistry.all().firstOrNull { it.id == taskId }
            ?: throw IllegalArgumentException("Task not found")
    return executor.execute(task, project.context, args)
  }

  fun getExecutionFlow(executionId: ExecutionId): Flow<ExecutionEvent> {
    return eventCollector.getFlow(executionId)
  }
}
