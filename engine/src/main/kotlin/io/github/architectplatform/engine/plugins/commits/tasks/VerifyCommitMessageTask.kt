package io.github.architectplatform.engine.plugins.commits.tasks

import io.github.architectplatform.api.components.workflows.hooks.HooksWorkflow
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Environment
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.engine.plugins.commits.context.CommitsContext
import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern
import org.slf4j.LoggerFactory

class VerifyCommitMessageTask(
    private val context: CommitsContext,
) : Task {
  private val logger = LoggerFactory.getLogger(this::class.java)
  override val id: String = "verify-commit-message-task"

  override fun phase(): HooksWorkflow = HooksWorkflow.COMMIT_MSG

  override fun execute(
      environment: Environment,
      projectContext: ProjectContext,
      args: List<String>
  ): TaskResult {
    logger.info("Running VerifyCommitMessageTask with context: $context")
    logger.debug("Project Context: $projectContext")
    val commitFilePath =
        args.getOrNull(0) ?: return TaskResult.failure("No commit message file path provided")

    val commitMessage: String =
        try {
          Files.readString(Paths.get(projectContext.dir.toString(), commitFilePath), Charsets.UTF_8)
              .trim()
        } catch (e: Exception) {
          return TaskResult.failure("Failed to read commit message: ${e.message}")
        }

    logger.debug("Verifying Commit Message: $commitMessage")

    val pattern =
        Pattern.compile(
            // Matches types like feat, fix, chore, etc.
            // Optional scope in parentheses, optional ! for breaking change
            // Followed by colon and message
            "^(feat|fix|chore|docs|style|refactor|perf|test|build|ci|revert)(\\([a-zA-Z0-9_-]+\\))?(!)?: .+")

    if (!pattern.matcher(commitMessage).matches()) {
      return TaskResult.failure(
          "❌ Commit message does not follow Conventional Commits.\n" +
              "Expected format: <type>(<scope>)!: <description>\n" +
              "Example: feat(parser)!: add ability to parse arrays")
    }

    return TaskResult.success("✅ Commit message verified successfully")
  }
}
