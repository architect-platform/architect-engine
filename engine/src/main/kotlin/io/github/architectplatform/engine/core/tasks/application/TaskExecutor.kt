package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskResult
import kotlinx.coroutines.flow.Flow

interface TaskExecutor {
  fun execute(task: Task, context: ProjectContext, args: List<String>): Flow<TaskResult>
}
