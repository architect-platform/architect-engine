package io.github.architectplatform.engine.core.project.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProjectFactoryTest {
    @Test
    fun testCreateProject() {
        val factory = ProjectFactory()
        val project = factory.create("TestProject", "/path/to/project")
        assertEquals("TestProject", project.name)
        assertEquals("/path/to/project", project.path)
    }
}

