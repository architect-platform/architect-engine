package io.github.architectplatform.engine.workflows.hooks.tasks

import io.github.architectplatform.api.execution.CommandExecutor
import io.github.architectplatform.api.phase.Phase
import io.github.architectplatform.api.project.ProjectContext
import io.github.architectplatform.api.tasks.Task
import io.github.architectplatform.api.tasks.TaskResult
import io.github.architectplatform.api.workflows.core.CoreWorkflow
import io.github.architectplatform.engine.execution.ResourceExtractor
import java.nio.file.Paths

class HooksInstallTask : Task {
	override fun phase(): Phase = CoreWorkflow.INIT
	override val id: String = "hooks-install"

	override fun execute(ctx: ProjectContext): TaskResult {
		println("Installing hooks...")
		val resourceRoot = "hooks"
		val projectPath = ctx.dir
		val hooksDir = Paths.get(projectPath.toString(), ".git", "hooks").toAbsolutePath()
		val resourceExtractor = ctx.service(ResourceExtractor::class.java)
		val commandExecutor = ctx.service(CommandExecutor::class.java)
		resourceExtractor.copyDirectoryFromResources(resourceRoot, hooksDir)
		resourceExtractor.listResourceFiles(resourceRoot).forEach { file ->
			val fileName = file.substringAfterLast("/")
			commandExecutor.execute("chmod +x $hooksDir/$fileName")
		}
		return TaskResult.success("Hooks installed successfully")
	}
}

