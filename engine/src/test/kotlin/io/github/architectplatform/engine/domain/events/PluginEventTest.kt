package io.github.architectplatform.engine.domain.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PluginEventTest {
    @Test
    fun testPluginEventTypeValues() {
        val types = PluginEventType.values()
        assertTrue(types.contains(PluginEventType.DOWNLOAD_STARTED))
        assertTrue(types.contains(PluginEventType.LOADED))
    }

    @Test
    fun testPluginEventInterface() {
        val event = object : PluginEvent {
            override val pluginId = "plugin1"
            override val pluginEventType = PluginEventType.LOADED
        }
        assertEquals("plugin1", event.pluginId)
        assertEquals(PluginEventType.LOADED, event.pluginEventType)
    }
}

