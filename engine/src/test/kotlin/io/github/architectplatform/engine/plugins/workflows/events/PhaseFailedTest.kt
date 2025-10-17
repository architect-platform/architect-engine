package io.github.architectplatform.engine.plugins.workflows.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PhaseFailedTest {
    @Test
    fun testPhaseFailedEvent() {
        val event = PhaseFailed("phase1", "Error occurred")
        assertEquals("phase1", event.phaseId)
        assertEquals("Error occurred", event.errorMessage)
    }
}

