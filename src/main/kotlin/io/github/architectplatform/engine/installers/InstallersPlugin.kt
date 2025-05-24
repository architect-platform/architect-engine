package io.github.architectplatform.engine.installers

import io.github.architectplatform.api.execution.CommandExecutor
import io.github.architectplatform.api.execution.ResourceExtractor
import io.github.architectplatform.api.phase.SimpleTask
import io.github.architectplatform.api.plugins.ArchitectPlugin
import io.github.architectplatform.api.project.ProjectContext
import io.github.architectplatform.api.tasks.TaskRegistry
import io.github.architectplatform.api.tasks.TaskResult
import io.github.architectplatform.api.workflows.core.CoreWorkflow
import jakarta.inject.Singleton
import java.io.File
import java.nio.file.Paths

@Singleton
class InstallersPlugin : ArchitectPlugin<InstallersContext> {
	override val id: String = "installers-plugin"
	override val contextKey: String = "installers"
	override val ctxClass: Class<InstallersContext> = InstallersContext::class.java
	override lateinit var context: InstallersContext

	override fun register(registry: TaskRegistry) {
		registry.add(
			SimpleTask(
				id = "installers",
				phase = CoreWorkflow.INIT,
				task = ::copyInstallers,
			)
		)
	}

	private fun copyInstallers(projectContext: ProjectContext): TaskResult {
		val installersDir = Paths.get(projectContext.dir.toString(), ".installers")
		val resourceRoot = "installers"
		val resourceExtractor = projectContext.service(ResourceExtractor::class.java)
		val commandExecutor = projectContext.service(CommandExecutor::class.java)
		resourceExtractor.copyDirectoryFromResources(this.javaClass.classLoader, resourceRoot, installersDir)
		File(installersDir.toString()).listFiles()?.forEach { file ->
			val content = file.readText()
			val replacedContent = content
				.replace("{{owner}}", context.owner)
				.replace("{{name}}", context.name)
				.replace("{{applicationName}}", context.applicationName)
				.replace("{{assetType}}", context.assetType)
			file.writeText(replacedContent)
			commandExecutor.execute("chmod +x ${file.path}")
		}
		return TaskResult.success("Installers copied to ${installersDir.toAbsolutePath()}")
	}
}

