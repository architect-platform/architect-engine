package io.github.architectplatform.engine.plugins.workflows.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PhaseStartedTest {
    @Test
    fun testPhaseStartedEvent() {
        val event = PhaseStarted("phase1")
        assertEquals("phase1", event.phaseId)
    }
}

