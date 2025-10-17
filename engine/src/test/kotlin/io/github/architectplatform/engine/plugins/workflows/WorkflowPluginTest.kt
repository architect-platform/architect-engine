package io.github.architectplatform.engine.plugins.workflows

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WorkflowPluginTest {
    @Test
    fun testWorkflowPluginProperties() {
        val plugin = WorkflowPlugin()
        assertEquals("workflow-plugin", plugin.id)
    }
}

