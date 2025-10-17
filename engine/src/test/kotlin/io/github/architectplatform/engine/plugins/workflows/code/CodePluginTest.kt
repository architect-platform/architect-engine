package io.github.architectplatform.engine.plugins.workflows.code

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CodePluginTest {
    @Test
    fun testCodePluginProperties() {
        val plugin = CodePlugin()
        assertEquals("code-plugin", plugin.id)
    }
}

