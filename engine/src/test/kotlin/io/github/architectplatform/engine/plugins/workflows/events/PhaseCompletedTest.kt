package io.github.architectplatform.engine.plugins.workflows.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PhaseCompletedTest {
    @Test
    fun testPhaseCompletedEvent() {
        val event = PhaseCompleted("phase1", true)
        assertEquals("phase1", event.phaseId)
        assertTrue(event.success)
    }
}

