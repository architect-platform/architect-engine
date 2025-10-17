package io.github.architectplatform.engine.plugins.workflows.hooks

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class HooksPluginTest {
    @Test
    fun testHooksPluginProperties() {
        val plugin = HooksPlugin()
        assertEquals("hooks-plugin", plugin.id)
    }
}

