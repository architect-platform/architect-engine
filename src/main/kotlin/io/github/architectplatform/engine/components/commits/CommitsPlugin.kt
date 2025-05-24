package io.github.architectplatform.engine.components.commits

import io.github.architectplatform.api.components.execution.CommandExecutor
import io.github.architectplatform.api.components.execution.ResourceExtractor
import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.api.core.tasks.TaskResult
import io.github.architectplatform.api.core.tasks.impl.TaskWithArgs
import io.github.architectplatform.api.components.workflows.hooks.HooksWorkflow
import jakarta.inject.Singleton

@Singleton
class CommitsPlugin : ArchitectPlugin<CommitsContext> {
	override val id: String = "commits-plugin"
	override val contextKey: String = "commits"
	override val ctxClass: Class<CommitsContext> = CommitsContext::class.java
	override lateinit var context: CommitsContext

	override fun register(registry: TaskRegistry) {
		registry.add(
			TaskWithArgs(
				id = "verify-commit-message-task",
				phase = HooksWorkflow.COMMIT_MSG,
				task = ::verifyCommitMessage,
			)
		)
	}

	private fun verifyCommitMessage(projectContext: ProjectContext, args: List<String>): TaskResult {
		val commitMessage = args.getOrNull(0) ?: return TaskResult.failure("No commit message provided")
		println("Verifying commit message: $commitMessage")
		val convention = context.type
		val filename = "verify.sh"
		val resourceExtractor = projectContext.service(ResourceExtractor::class.java)
		val executor = projectContext.service(CommandExecutor::class.java)
		resourceExtractor.copyFileFromResources(
			this.javaClass.classLoader,
			"commits/$convention/verify.sh",
			projectContext.dir,
			filename
		)
		try {
			executor.execute("./$filename '$commitMessage'", projectContext.dir.toString())
		} catch (e: Exception) {
			return TaskResult.failure("Commit message verification failed: ${e.message}")
		} finally {
			// Clean up the script file after execution
			executor.execute("rm -f $filename", projectContext.dir.toString())
		}

		return TaskResult.success("Commit message verified successfully")
	}
}

