package io.github.architectplatform.engine.core.project.interfaces.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RegisterProjectRequestTest {
    @Test
    fun testRegisterProjectRequestConstruction() {
        val request = RegisterProjectRequest("TestProject", "/path/to/project")
        assertEquals("TestProject", request.name)
        assertEquals("/path/to/project", request.path)
    }
}

