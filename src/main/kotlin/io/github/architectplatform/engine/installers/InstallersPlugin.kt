package io.github.architectplatform.engine.installers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.architectplatform.api.execution.CommandExecutor
import io.github.architectplatform.api.phase.SimpleTask
import io.github.architectplatform.api.plugins.ArchitectPlugin
import io.github.architectplatform.api.project.ProjectContext
import io.github.architectplatform.api.project.getKey
import io.github.architectplatform.api.tasks.TaskRegistry
import io.github.architectplatform.api.tasks.TaskResult
import io.github.architectplatform.api.workflows.core.CoreWorkflow
import io.github.architectplatform.engine.execution.ResourceExtractor
import jakarta.inject.Singleton
import java.io.File
import java.nio.file.Paths

@Singleton
class InstallersPlugin : ArchitectPlugin {
	override val id: String = "installers-plugin"

	override fun register(registry: TaskRegistry) {
		registry.add(
			SimpleTask(
				id = "installers",
				phase = CoreWorkflow.INIT,
				task = ::copyInstallers,
			)
		)
	}

	data class InstallersContext(
		val owner: String,
		val name: String,
		val applicationName: String,
		val assetType: String,
	)

	private fun copyInstallers(context: ProjectContext): TaskResult {
		val installersContextMap = context.config.getKey<Map<String, Any>>("installers")
			?: return TaskResult.success("Installers context not found in config, skipping...")
		println("Installers context map: $installersContextMap")
		val objectMapper = ObjectMapper().registerKotlinModule()
		val installersContext = objectMapper.convertValue(
			installersContextMap,
			InstallersContext::class.java
		)

		val installersDir = Paths.get(context.dir.toString(), ".installers")
		val resourceRoot = "installers"
		val resourceExtractor = context.service(ResourceExtractor::class.java)
		val commandExecutor = context.service(CommandExecutor::class.java)
		resourceExtractor.copyDirectoryFromResources(resourceRoot, installersDir)
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

