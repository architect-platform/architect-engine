package io.github.architectplatform.engine.core.project.interfaces.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConfigDTOTest {
    @Test
    fun testConfigDTOConstruction() {
        val config = ConfigDTO("TestConfig", mapOf("key" to "value"))
        assertEquals("TestConfig", config.name)
        assertEquals("value", config.properties["key"])
    }
}

