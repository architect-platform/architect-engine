package io.github.architectplatform.engine.core.project.interfaces

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ProjectsApiControllerTest {
    @Test
    fun testGetProject() {
        val controller = ProjectsApiController()
        val mockService = mock<ProjectService>()
        whenever(mockService.getProject("TestProject"))
            .thenReturn(Project("TestProject", "/path/to/project"))
        controller.projectService = mockService

        val project = controller.getProject("TestProject")
        assertEquals("TestProject", project.name)
        assertEquals("/path/to/project", project.path)
    }
}

