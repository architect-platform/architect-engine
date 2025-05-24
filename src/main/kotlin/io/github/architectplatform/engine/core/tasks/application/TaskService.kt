package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.tasks.Task
import io.github.architectplatform.api.tasks.TaskRegistry
import io.github.architectplatform.api.tasks.TaskResult
import io.github.architectplatform.engine.core.project.app.ProjectService
import jakarta.inject.Singleton

@Singleton
class TaskService(
	private val taskRegistry: TaskRegistry,
	private val projectService: ProjectService,
) {

	fun getAllTasks(): List<Task> {
		return taskRegistry.all().filter { it.phase() == null }
			.sortedBy { it.id }
	}

	fun getTaskById(taskId: String): Task? {
		println("GetTaskById: $taskId - ${taskRegistry.all()}")
		return taskRegistry.all().firstOrNull { it.id == taskId }
	}

	fun executeTask(taskId: String, projectName: String, args: List<String>): TaskResult {
		val project = projectService.getProject(projectName) ?: throw IllegalArgumentException("Project not found")
		val task = getTaskById(taskId) ?: throw IllegalArgumentException("Task not found")
		return task.execute(project.context, args)
	}


}