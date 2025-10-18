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
import io.github.architectplatform.engine.plugins.workflows.events.PhaseTaskFailed
import io.github.architectplatform.engine.plugins.workflows.events.PhaseTaskStarted
import org.slf4j.LoggerFactory

class PhaseExecutorTask(private val phase: Phase, private val registry: TaskRegistry) : Task {

  private val logger = LoggerFactory.getLogger(this::class.java)

  override val id: String = phase.id

  override fun description(): String = phase.description()

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
          val result = task.execute(environment, projectContext, args)
          if (result.success) {
            logger.debug("Task ${task.id} in phase ${phase.id} succeeded: ${result.message}")
            environment.publish(PhaseTaskCompleted(task, phase, result))
          } else {
            logger.error("Task ${task.id} in phase ${phase.id} failed: ${result.message}")
            result.results.forEach { logger.error("  Sub-result: ${it.message}") }
            environment.publish(
                PhaseTaskFailed(taskId = task.id, reason = result.message ?: "Unknown error"))
          }
          result
        }

    val success = results.all { it.success }
    if (!success) {
      logger.error("Phase ${phase.id} failed due to task failures")
      environment.publish(PhaseTaskFailed(phase.id, "Some tasks in phase failed", results))
      return TaskResult.failure("Phase execution failed for phase: $phase", results = results)
    }
    environment.publish(PhaseCompleted(phase, results))
    return TaskResult.success("Executed phase: $phase", results = results)
  }

  override fun toString(): String {
    return "PhaseExecutorTask(id='$id', phase=$phase)"
  }
}
