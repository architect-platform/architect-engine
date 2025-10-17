package io.github.architectplatform.engine.core.project.app

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ProjectServiceTest {
    @Test
    fun testGetProjectDetails() {
        val service = ProjectService(mock())
        whenever(service.getProject("TestProject"))
            .thenReturn(Project("TestProject", "/path/to/project"))

        val project = service.getProject("TestProject")
        assertEquals("TestProject", project.name)
        assertEquals("/path/to/project", project.path)
    }
}

