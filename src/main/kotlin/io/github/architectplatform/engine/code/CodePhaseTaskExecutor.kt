package io.github.architectplatform.engine.code

import io.github.architectplatform.api.assets.code.CodePhaseTask
import io.github.architectplatform.api.assets.code.CodePhases
import io.github.architectplatform.api.core.CorePhaseTask
import io.github.architectplatform.api.core.CorePhases
import io.github.architectplatform.api.project.ProjectContext
import io.github.architectplatform.api.tasks.TaskRegistry
import io.github.architectplatform.api.tasks.TaskResult

class CodePhaseTaskExecutor(
	private val codePhase: CodePhases,
	private val registry: TaskRegistry,
	override val id: String = "CODE-" + codePhase.phaseName,
	override val phase: CorePhases = codePhase.phase!!,

	) : CorePhaseTask {
	override fun execute(ctx: ProjectContext): TaskResult {
		println("CodeAssetPhaseExecutorTask: Executing phase: $phase")
		val children = registry.all().filterIsInstance<CodePhaseTask>()
			.filter { it.phase == codePhase }

		println("CodeAssetPhaseExecutorTask: Found ${children.size} tasks for phase: $phase")
		children.forEach { it.execute(ctx) }

		println("CodeAssetPhaseExecutorTask: Finished executing tasks for phase: $phase")
		return TaskResult.Success("Executed phase: $phase")
	}
}