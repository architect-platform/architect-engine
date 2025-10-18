package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.engine.core.project.app.ProjectService
import io.github.architectplatform.engine.core.project.domain.Project
import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.github.architectplatform.engine.domain.events.ExecutionId
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class TaskService(
    private val projectService: ProjectService,
    private val executor: TaskExecutor,
    private val eventCollector: ExecutionEventCollector
) {

  fun getAllTasks(projectName: String): List<Task> {
    val project =
        projectService.getProject(projectName)
            ?: throw IllegalArgumentException("Project not found")
    return project.taskRegistry.all().sortedBy { it.id }
  }

  fun getTaskById(projectName: String, taskId: String): Task {
    val project =
        projectService.getProject(projectName)
            ?: throw IllegalArgumentException("Project not found")
    return project.taskRegistry.get(taskId) ?: throw IllegalArgumentException("Task not found")
  }

  fun executeTask(projectName: String, taskId: String, args: List<String>): ExecutionId {
    val project =
        projectService.getProject(projectName)
            ?: throw IllegalArgumentException("Project not found")

    fun executeRecursivelyOverSubprojectsFirst(project: Project, args: List<String>): ExecutionId {
      project.subProjects.forEach { subProject ->
        executeRecursivelyOverSubprojectsFirst(subProject, args)
      }
      val task =
          project.taskRegistry.all().firstOrNull { it.id == taskId }
              ?: throw IllegalArgumentException("Task not found")
      return executor.execute(project, task, project.context, args)
    }

    return executeRecursivelyOverSubprojectsFirst(project, args)
  }

  fun getExecutionFlow(executionId: ExecutionId): Flow<ArchitectEvent<ExecutionEvent>> {
    return eventCollector.getFlow(executionId)
  }
}
