package io.github.architectplatform.engine.plugins.installers.tasks

import io.github.architectplatform.api.components.execution.CommandExecutor
import io.github.architectplatform.api.components.execution.ResourceExtractor
import io.github.architectplatform.api.components.workflows.core.CoreWorkflow
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Environment
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase
import io.github.architectplatform.engine.plugins.installers.context.InstallersContext
import java.io.File
import java.nio.file.Paths
import org.slf4j.LoggerFactory

class CopyInstallerTask(private val installersContext: InstallersContext) : Task {

  override val id: String = "copy-installers-task"

  override fun phase(): Phase = CoreWorkflow.INIT

  private val logger = LoggerFactory.getLogger(this::class.java)

  override fun execute(
      environment: Environment,
      projectContext: ProjectContext,
      args: List<String>
  ): TaskResult {
    if (!installersContext.enabled) {
      logger.info("Installers are not enabled, skipping copy task.")
      logger.info("ProjectContext: $projectContext")
      logger.info("InstallersContext: $installersContext")
      logger.info("ProjectContext: $projectContext")
      return TaskResult.success("Installers are not enabled, skipping copy task.")
    }
    val installersDir = Paths.get(projectContext.dir.toString(), ".installers")
    val resourceRoot = "installers"
    val resourceExtractor = environment.service(ResourceExtractor::class.java)
    val commandExecutor = environment.service(CommandExecutor::class.java)
    resourceExtractor.copyDirectoryFromResources(
        this.javaClass.classLoader, resourceRoot, installersDir)
    File(installersDir.toString()).listFiles()?.forEach { file ->
      val content = file.readText()
      val replacedContent =
          content
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
