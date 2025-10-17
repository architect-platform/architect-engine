package io.github.architectplatform.engine.plugins.installers.context

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InstallersContextTest {
  @Test
  fun testInstallersContextConstruction() {
    val context = InstallersContext("/path/to/installers")
    assertEquals("/path/to/installers", context.installersPath)
  }
}
