package io.github.architectplatform.engine.components.installers.tasks

import io.github.architectplatform.api.components.execution.CommandExecutor
import io.github.architectplatform.api.components.execution.ResourceExtractor
import io.github.architectplatform.api.components.workflows.core.CoreWorkflow
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase
import io.github.architectplatform.engine.components.installers.context.InstallersContext
import java.io.File
import java.nio.file.Paths

class CopyInstallerTask(private val installersContext: InstallersContext) : Task {

	override val id: String = "copy-installers-task"
	override fun phase(): Phase = CoreWorkflow.INIT

	override fun execute(ctx: ProjectContext, args: List<String>): TaskResult {
		val installersDir = Paths.get(ctx.dir.toString(), ".installers")
		val resourceRoot = "installers"
		val resourceExtractor = ctx.service(ResourceExtractor::class.java)
		val commandExecutor = ctx.service(CommandExecutor::class.java)
		resourceExtractor.copyDirectoryFromResources(this.javaClass.classLoader, resourceRoot, installersDir)
		File(installersDir.toString()).listFiles()?.forEach { file ->
			val content = file.readText()
			val replacedContent = content
				.replace("{{owner}}", installersContext.owner)
				.replace("{{name}}", installersContext.name)
				.replace("{{applicationName}}", installersContext.applicationName)
				.replace("{{assetType}}", installersContext.assetType)
			file.writeText(replacedContent)
			commandExecutor.execute("chmod +x ${file.path}")
		}
		return TaskResult.success("Installers copied to ${installersDir.toAbsolutePath()}")
	}
}