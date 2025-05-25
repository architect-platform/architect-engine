package io.github.architectplatform.engine.components.workflows.hooks.tasks

import io.github.architectplatform.api.components.execution.CommandExecutor
import io.github.architectplatform.api.components.execution.ResourceExtractor
import io.github.architectplatform.api.core.tasks.phase.Phase
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.components.workflows.core.CoreWorkflow
import java.nio.file.Paths

class HooksInstallTask : Task {
	override fun phase(): Phase = CoreWorkflow.INIT
	override val id: String = "hooks-install"

	override fun execute(ctx: ProjectContext, args: List<String>): TaskResult {
		println("Installing hooks...")
		val resourceRoot = "hooks"
		val projectPath = ctx.dir
		val hooksDir = Paths.get(projectPath.toString(), ".git", "hooks").toAbsolutePath()
		val resourceExtractor = ctx.service(ResourceExtractor::class.java)
		val commandExecutor = ctx.service(CommandExecutor::class.java)
		resourceExtractor.copyDirectoryFromResources(this.javaClass.classLoader, resourceRoot, hooksDir)
		resourceExtractor.listResourceFiles(this.javaClass.classLoader, resourceRoot).forEach { file ->
			val fileName = file.substringAfterLast("/")
			commandExecutor.execute("chmod +x $hooksDir/$fileName")
		}
		return TaskResult.success("Hooks installed successfully")
	}
}

