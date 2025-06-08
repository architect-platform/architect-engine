package io.github.architectplatform.engine.plugins.workflows.hooks.tasks

import io.github.architectplatform.api.components.execution.CommandExecutor
import io.github.architectplatform.api.components.execution.ResourceExtractor
import io.github.architectplatform.api.components.workflows.core.CoreWorkflow
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Environment
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase
import java.nio.file.Paths

class HooksInstallTask : Task {
  override fun phase(): Phase = CoreWorkflow.INIT

  override val id: String = "hooks-install"

  override fun execute(
      environment: Environment,
      projectContext: ProjectContext,
      args: List<String>
  ): TaskResult {
    val resourceRoot = "hooks"
    val projectPath = projectContext.dir
    val hooksDir = Paths.get(projectPath.toString(), ".git", "hooks").toAbsolutePath()
    val resourceExtractor = environment.service(ResourceExtractor::class.java)
    val commandExecutor = environment.service(CommandExecutor::class.java)
    resourceExtractor.copyDirectoryFromResources(this.javaClass.classLoader, resourceRoot, hooksDir)
    resourceExtractor.listResourceFiles(this.javaClass.classLoader, resourceRoot).forEach { file ->
      val fileName = file.substringAfterLast("/")
      commandExecutor.execute("chmod +x $hooksDir/$fileName")
    }
    return TaskResult.success("Hooks installed successfully")
  }
}
