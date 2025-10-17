package io.github.architectplatform.engine

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test

class ApplicationTest {
  @Test
  fun testApplicationMain() {
    // This test ensures the main entry point can be called without error
    assertDoesNotThrow { main(arrayOf()) }
  }
}
