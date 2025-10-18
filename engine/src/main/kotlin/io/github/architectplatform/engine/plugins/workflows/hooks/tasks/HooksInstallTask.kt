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

  private fun findGitDir(projectPath: String): String? {
    var currentPath = Paths.get(projectPath).toAbsolutePath()

    while (true) {
      val gitPath = currentPath.resolve(".git")
      if (gitPath.toFile().exists() && gitPath.toFile().isDirectory) {
        return gitPath.toString()
      }

      val parentPath = currentPath.parent ?: break
      currentPath = parentPath
    }
    return null
  }

  override fun execute(
      environment: Environment,
      projectContext: ProjectContext,
      args: List<String>
  ): TaskResult {
    val resourceRoot = "hooks"
    val gitDir = findGitDir(projectContext.dir.toString())
        ?: return TaskResult.failure("No .git directory found in project hierarchy")
    val hooksDir = Paths.get(gitDir, "hooks").toAbsolutePath()
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
