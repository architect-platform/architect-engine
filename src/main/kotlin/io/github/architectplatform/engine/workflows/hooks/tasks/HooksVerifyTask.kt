package io.github.architectplatform.engine.workflows.hooks.tasks

import io.github.architectplatform.api.phase.Phase
import io.github.architectplatform.api.project.ProjectContext
import io.github.architectplatform.api.tasks.Task
import io.github.architectplatform.api.tasks.TaskResult
import io.github.architectplatform.api.workflows.core.CoreWorkflow
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths

class HooksVerifyTask : Task {
	override fun phase(): Phase = CoreWorkflow.VERIFY
	override val id: String = "hooks-verify"

	override fun execute(ctx: ProjectContext): TaskResult {
		println("Verifying hooks...")
		val projectPath = ctx.dir
		val hooksDir = Paths.get(projectPath.toString(), ".git", "hooks").toAbsolutePath()
		if (!Files.exists(hooksDir)) {
			println("❌ .git/hooks directory does not exist.")
			return TaskResult.failure("Hooks directory does not exist")
		}

		val resourceRoot = "hooks"
		val classLoader = Thread.currentThread().contextClassLoader

		val resourceUrl = classLoader.getResource(resourceRoot)
		if (resourceUrl == null) {
			println("❌ Could not find resources under $resourceRoot")
			return TaskResult.failure("Resource not found")
		}

		val expectedHooks: List<String> = when (resourceUrl.protocol) {
			"file" -> {
				// Running from source
				Files.list(Paths.get(resourceUrl.toURI()))
					.map { it.fileName.toString() }
					.toList()
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
				println("❌ Unsupported resource protocol: ${resourceUrl.protocol}")
				return TaskResult.failure("Unsupported resource protocol")
			}
		}

		var allPresent = true

		for (hook in expectedHooks) {
			val hookPath = hooksDir.resolve(hook)
			if (!Files.exists(hookPath)) {
				println("❌ Missing hook: $hook")
				allPresent = false
			} else if (!Files.isExecutable(hookPath)) {
				println("⚠️ Hook exists but is not executable: $hook")
				allPresent = false
			} else {
				println("✅ Hook present and executable: $hook")
			}
		}

		if (allPresent) {
			println("✅ All expected Git hooks are properly installed.")
			return TaskResult.success("All hooks verified successfully")
		} else {
			println("⚠️ Some Git hooks are missing or not executable.")
			return TaskResult.failure("Some hooks are missing or not executable")
		}
	}
}