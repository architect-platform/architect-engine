package io.github.architectplatform.engine.core.tasks.interfaces

import io.github.architectplatform.engine.core.tasks.application.TaskService
import io.github.architectplatform.engine.core.tasks.interfaces.dto.TaskDTO
import io.github.architectplatform.engine.core.tasks.interfaces.dto.TaskResultDTO
import io.github.architectplatform.engine.core.tasks.interfaces.dto.toDTO
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn

@Controller("/api/projects/{projectName}/tasks")
@ExecuteOn(TaskExecutors.IO)
class TasksApiController(
	private val taskService: TaskService,
) {

	@Get
	fun getAllTasks(@PathVariable projectName: String): List<TaskDTO> {
		println("Getting all tasks for project: $projectName")
		return taskService.getAllTasks().map { it.toDTO() }.also { println("Tasks found: $it") }
	}

	@Get("/{taskName}")
	fun getTask(
		@PathVariable projectName: String,
		@PathVariable taskName: String,
	): TaskDTO {
		println("Getting task: $taskName for project: $projectName")
		val task = taskService.getTaskById(taskName)!!
		return task.toDTO().also { println("Task found: $it") }
	}

	@Post("/{taskName}")
	fun executeTask(
		@PathVariable projectName: String,
		@PathVariable taskName: String,
		@Body args: List<String> = emptyList(),
	): TaskResultDTO {
		println("Executing task: $taskName for project: $projectName")
		val result = taskService.executeTask(taskName, projectName, args)
		return result.toDTO().also { println("Task executed: $it") }
	}

}