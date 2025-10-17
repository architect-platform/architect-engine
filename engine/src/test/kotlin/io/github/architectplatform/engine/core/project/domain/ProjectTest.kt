package io.github.architectplatform.engine.core.project.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProjectTest {
    @Test
    fun testProjectConstruction() {
        val project = Project("TestProject", "/path/to/project")
        assertEquals("TestProject", project.name)
        assertEquals("/path/to/project", project.path)
    }

    @Test
    fun testProjectEquality() {
        val project1 = Project("TestProject", "/path/to/project")
        val project2 = Project("TestProject", "/path/to/project")
        assertEquals(project1, project2)
    }
}

