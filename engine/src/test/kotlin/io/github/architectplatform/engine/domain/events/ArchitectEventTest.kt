package io.github.architectplatform.engine.domain.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ArchitectEventTest {
    @Test
    fun testAbstractArchitectEventConstruction() {
        val event = object : AbstractArchitectEvent<String>("event1", "TestEvent") {}
        assertEquals("event1", event.id)
        assertEquals("TestEvent", event.event)
    }

    @Test
    fun testGenerateExecutionId() {
        val id = generateExecutionId()
        assertNotNull(id)
        assertEquals(8, id.length)
    }
}

