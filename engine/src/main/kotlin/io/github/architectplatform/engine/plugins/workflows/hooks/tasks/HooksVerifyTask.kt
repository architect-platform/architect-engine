package io.github.architectplatform.engine.plugins.workflows.hooks.tasks

import io.github.architectplatform.api.components.workflows.core.CoreWorkflow
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Environment
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.phase.Phase
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths

class HooksVerifyTask : Task {
  override fun phase(): Phase = CoreWorkflow.VERIFY

  override val id: String = "hooks-verify"

  override fun execute(
      environment: Environment,
      projectContext: ProjectContext,
      args: List<String>
  ): TaskResult {
    val projectPath = projectContext.dir
    val hooksDir = Paths.get(projectPath.toString(), ".git", "hooks").toAbsolutePath()
    if (!Files.exists(hooksDir)) {
      return TaskResult.failure("Hooks directory does not exist")
    }

    val resourceRoot = "hooks"
    val classLoader = Thread.currentThread().contextClassLoader

    val resourceUrl = classLoader.getResource(resourceRoot)
    if (resourceUrl == null) {
      return TaskResult.failure("Resource $resourceRoot not found")
    }

    val expectedHooks: List<String> =
        when (resourceUrl.protocol) {
          "file" -> {
            // Running from source
            Files.list(Paths.get(resourceUrl.toURI())).map { it.fileName.toString() }.toList()
          }

          "jar" -> {
            // Running from a packaged JAR
            val jarPath = (resourceUrl.path.substringBefore("!")).removePrefix("file:")
            val jarFile = FileSystems.newFileSystem(Paths.get(jarPath), null as ClassLoader?)
            Files.walk(jarFile.getPath("/$resourceRoot"))
                .filter { Files.isRegularFile(it) }
                .map { it.fileName.toString() }
                .toList()
          }

          else -> {
            return TaskResult.failure("Unsupported resource protocol: ${resourceUrl.protocol}")
          }
        }

    for (hook in expectedHooks) {
      val hookPath = hooksDir.resolve(hook)
      if (!Files.exists(hookPath)) {
        return TaskResult.failure("Hook is missing: $hook")
      } else if (!Files.isExecutable(hookPath)) {
        return TaskResult.failure("Hook is not executable: $hook")
      }
    }
    return TaskResult.success("All hooks are present and executable")
  }
}
