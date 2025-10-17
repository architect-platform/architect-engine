package io.github.architectplatform.engine.plugins.workflows.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PhaseTaskStartedTest {
    @Test
    fun testPhaseTaskStartedEvent() {
        val event = PhaseTaskStarted("task1")
        assertEquals("task1", event.taskId)
    }
}

