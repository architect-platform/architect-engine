package io.github.architectplatform.engine.plugins.workflows.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PhaseTaskFailedTest {
    @Test
    fun testPhaseTaskFailedEvent() {
        val event = PhaseTaskFailed("task1", "Error occurred")
        assertEquals("task1", event.taskId)
        assertEquals("Error occurred", event.errorMessage)
    }
}

