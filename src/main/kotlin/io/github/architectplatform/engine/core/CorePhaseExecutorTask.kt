package io.github.architectplatform.engine.core

import io.github.architectplatform.api.core.CorePhaseTask
import io.github.architectplatform.api.core.CorePhases
import io.github.architectplatform.api.project.ProjectContext
import io.github.architectplatform.api.tasks.Task
import io.github.architectplatform.api.tasks.TaskRegistry
import io.github.architectplatform.api.tasks.TaskResult

class CorePhaseExecutorTask(
	private val phase: CorePhases,
	private val registry: TaskRegistry,
	override val id: String = phase.phaseName,
) : Task {
	override fun execute(ctx: ProjectContext): TaskResult {
		println("CorePhaseExecutorTask: Executing phase: $phase")
		println("All tasks in registry: ${registry.all().joinToString(", ") { it.id }}")
		val children = registry.all().filterIsInstance<CorePhaseTask>()
			.filter { println("CorePhaseExecutorTask: Checking task: ${it.id} for phase: $phase"); it.phase == phase }

		println("CorePhaseExecutorTask: Found ${children.size} tasks for phase: $phase")
		children.forEach { it.execute(ctx) }

		println("CorePhaseExecutorTask: Finished executing tasks for phase: $phase")
		return TaskResult.Success("Executed phase: $phase")
	}

	override fun toString(): String {
		return "CorePhaseExecutorTask(phase=$phase, id='$id')"
	}
}