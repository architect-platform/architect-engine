package io.github.architectplatform.engine.plugins.workflows.core

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CorePluginTest {
    @Test
    fun testCorePluginProperties() {
        val plugin = CorePlugin()
        assertEquals("core-plugin", plugin.id)
    }
}

