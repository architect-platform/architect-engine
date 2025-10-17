package io.github.architectplatform.engine.domain.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ExecutionTaskEventTest {
    @Test
    fun testExecutionTaskEventInterface() {
        val event = object : ExecutionTaskEvent {
            override val executionId = "exec1"
            override val executionEventType = ExecutionEventType.STARTED
            override val success = true
            override val taskId = "task1"
        }
        assertEquals("exec1", event.executionId)
        assertEquals(ExecutionEventType.STARTED, event.executionEventType)
        assertTrue(event.success)
        assertEquals("task1", event.taskId)
    }
}

