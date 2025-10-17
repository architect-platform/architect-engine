package io.github.architectplatform.engine.core.project.app

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConfigLoaderTest {
    @Test
    fun testLoadConfig() {
        val loader = ConfigLoader()
        val config = loader.load("config.yaml")
        assertNotNull(config)
        assertEquals("TestConfig", config.name)
    }

    @Test
    fun testLoadInvalidConfig() {
        val loader = ConfigLoader()
        assertThrows<IllegalArgumentException> { loader.load("invalid.yaml") }
    }
}

