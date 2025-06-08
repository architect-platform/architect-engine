package io.github.architectplatform.engine.core.execution

import io.github.architectplatform.api.components.execution.CommandExecutor
import jakarta.inject.Singleton
import java.io.File

@Singleton
open class BashCommandExecutor : CommandExecutor {

  private fun executeCommand(command: String, workingDir: String? = null): Pair<Int, String> {
    val processBuilder = ProcessBuilder("sh", "-c", command)
    workingDir?.let { processBuilder.directory(File(it)) }
    val process = processBuilder.start()

    val output = process.inputStream.bufferedReader().readText()
    val exitCode = process.waitFor()
    return exitCode to output.trim()
  }

  override fun execute(command: String, workingDir: String?) {
    val (exitCode, result) = executeCommand(command, workingDir)
    if (exitCode != 0) {
      error("❌ Command failed with exit code $exitCode\nResult:\n$result")
    }
  }
}
