package io.github.architectplatform.engine.plugins.workflows.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PhaseTaskCompletedTest {
    @Test
    fun testPhaseTaskCompletedEvent() {
        val event = PhaseTaskCompleted("task1", true)
        assertEquals("task1", event.taskId)
        assertTrue(event.success)
    }
}

