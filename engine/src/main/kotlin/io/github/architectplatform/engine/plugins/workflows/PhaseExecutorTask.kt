package io.github.architectplatform.engine.plugins.workflows

import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Environment
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase
import io.github.architectplatform.engine.core.tasks.interfaces.dto.toDTO
import io.github.architectplatform.engine.plugins.workflows.events.PhaseCompleted
import io.github.architectplatform.engine.plugins.workflows.events.PhaseLoaded
import io.github.architectplatform.engine.plugins.workflows.events.PhaseStarted
import io.github.architectplatform.engine.plugins.workflows.events.PhaseTaskCompleted
import io.github.architectplatform.engine.plugins.workflows.events.PhaseTaskStarted

class PhaseExecutorTask(private val phase: Phase, private val registry: TaskRegistry) : Task {
  override val id: String = phase.id

  override fun phase(): Phase? = phase.parent()

  override fun depends(): List<String> = phase.depends()

  override fun execute(
      environment: Environment,
      projectContext: ProjectContext,
      args: List<String>
  ): TaskResult {
    environment.publish(PhaseStarted(phase))
    val children = registry.all().filter { it.phase()?.id == phase.id }

    environment.publish(PhaseLoaded(phase, children.map { it.toDTO() }))

    val results =
        children.map { task ->
          environment.publish(PhaseTaskStarted(task, phase))
          val taskResult = task.execute(environment, projectContext, args)
          environment.publish(PhaseTaskCompleted(task, phase, taskResult))
          taskResult
        }

    environment.publish(PhaseCompleted(phase, results))
    return TaskResult.success("Executed phase: $phase", results = results)
  }

  override fun toString(): String {
    return "PhaseExecutorTask(id='$id', phase=$phase)"
  }
}
