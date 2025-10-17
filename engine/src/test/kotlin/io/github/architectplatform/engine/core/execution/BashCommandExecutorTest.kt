package io.github.architectplatform.engine.core.execution

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BashCommandExecutorTest {
  @Test
  fun testExecuteCommand() {
    val executor = BashCommandExecutor()
    val result = executor.execute("echo Hello World")
    assertEquals("Hello World", result.output.trim())
    assertTrue(result.success)
  }

  @Test
  fun testExecuteInvalidCommand() {
    val executor = BashCommandExecutor()
    val result = executor.execute("invalid_command")
    assertFalse(result.success)
  }
}
