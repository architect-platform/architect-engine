package io.github.architectplatform.engine.core.project.interfaces.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProjectDTOTest {
    @Test
    fun testProjectDTOConstruction() {
        val project = ProjectDTO("TestProject", "/path/to/project")
        assertEquals("TestProject", project.name)
        assertEquals("/path/to/project", project.path)
    }
}

