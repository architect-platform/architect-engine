package io.github.architectplatform.engine.domain.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ExecutionEventTest {
    @Test
    fun testExecutionEventTypeValues() {
        val types = ExecutionEventType.values()
        assertTrue(types.contains(ExecutionEventType.STARTED))
        assertTrue(types.contains(ExecutionEventType.COMPLETED))
    }
}

