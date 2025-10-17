package io.github.architectplatform.engine.core.project.app

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ApplicationEnvironmentTest {
    @Test
    fun testEnvironmentProperties() {
        val environment = ApplicationEnvironment("dev", mapOf("key" to "value"))
        assertEquals("dev", environment.name)
        assertEquals("value", environment.properties["key"])
    }
}

