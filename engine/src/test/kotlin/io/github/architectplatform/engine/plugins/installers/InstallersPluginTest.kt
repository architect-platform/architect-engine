package io.github.architectplatform.engine.plugins.installers

import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.engine.plugins.installers.context.InstallersContext
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class InstallersPluginTest {
  @Test
  fun testPluginProperties() {
    val plugin = InstallersPlugin()
    assertEquals("installers-plugin", plugin.id)
    assertEquals("installers", plugin.contextKey)
    assertEquals(InstallersContext::class.java, plugin.ctxClass)
  }

  @Test
  fun testRegisterAddsTask() {
    val plugin = InstallersPlugin()
    val context = mock<InstallersContext>()
    plugin.context = context
    val registry = mock<TaskRegistry>()
    assertDoesNotThrow { plugin.register(registry) }
  }
}
