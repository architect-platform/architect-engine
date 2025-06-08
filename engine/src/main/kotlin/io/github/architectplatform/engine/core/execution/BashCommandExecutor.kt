package io.github.architectplatform.engine.core.execution

import io.github.architectplatform.api.components.execution.CommandExecutor
import jakarta.inject.Singleton
import java.io.File

@Singleton
open class BashCommandExecutor : CommandExecutor {

  private fun executeCommand(command: String, workingDir: String? = null): Pair<Int, String> {
    val processBuilder = ProcessBuilder("sh", "-c", command)
    workingDir?.let { processBuilder.directory(File(it)) }

    processBuilder.redirectErrorStream(true) // Merge stderr into stdout

    val process = processBuilder.start()

    val output = StringBuilder()
    val reader = process.inputStream.bufferedReader()

    // Read process output line by line
    val outputThread = Thread { reader.forEachLine { line -> output.appendLine(line) } }

    outputThread.start()
    val exitCode = process.waitFor()
    outputThread.join()

    return exitCode to output.toString().trim()
  }

  override fun execute(command: String, workingDir: String?) {
    val (exitCode, result) = executeCommand(command, workingDir)
    println("Command executed: $command, workingDir: $workingDir")
    println("Exit code: $exitCode")
    println("Result:\n$result")
    if (exitCode != 0) {
      error("❌ Command failed with exit code $exitCode\nResult:\n$result")
    }
  }
}
