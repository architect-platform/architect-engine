package io.github.architectplatform.engine.plugins.workflows.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PhaseLoadedTest {
    @Test
    fun testPhaseLoadedEvent() {
        val event = PhaseLoaded("phase1")
        assertEquals("phase1", event.phaseId)
    }
}

