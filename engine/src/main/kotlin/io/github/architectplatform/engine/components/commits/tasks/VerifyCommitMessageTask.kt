package io.github.architectplatform.engine.components.commits.tasks

import io.github.architectplatform.api.components.execution.CommandExecutor
import io.github.architectplatform.api.components.execution.ResourceExtractor
import io.github.architectplatform.api.components.workflows.hooks.HooksWorkflow
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.Task
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.engine.components.commits.context.CommitsContext

class VerifyCommitMessageTask(
	private val context: CommitsContext,
) : Task {
	override val id: String = "verify-commit-message-task"
	override fun phase(): HooksWorkflow = HooksWorkflow.COMMIT_MSG

	override fun execute(ctx: ProjectContext, args: List<String>): TaskResult {
		val commitMessage = args.getOrNull(0) ?: return TaskResult.failure("No commit message provided")
		println("Verifying commit message: $commitMessage")
		val convention = context.type
		val filename = "verify.sh"
		val resourceExtractor = ctx.service(ResourceExtractor::class.java)
		val executor = ctx.service(CommandExecutor::class.java)
		resourceExtractor.copyFileFromResources(
			this.javaClass.classLoader,
			"commits/$convention/verify.sh",
			ctx.dir,
			filename
		)
		try {
			executor.execute("./$filename '$commitMessage'", ctx.dir.toString())
		} catch (e: Exception) {
			return TaskResult.failure("Commit message verification failed: ${e.message}")
		} finally {
			// Clean up the script file after execution
			executor.execute("rm -f $filename", ctx.dir.toString())
		}

		return TaskResult.success("Commit message verified successfully")
	}
}