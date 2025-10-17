package io.github.architectplatform.engine.core.project.infra

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InMemoryProjectRepositoryTest {
    @Test
    fun testAddAndRetrieveProject() {
        val repository = InMemoryProjectRepository()
        val project = Project("TestProject", "/path/to/project")
        repository.add(project)
        val retrieved = repository.get("TestProject")
        assertEquals(project, retrieved)
    }

    @Test
    fun testRetrieveNonExistentProject() {
        val repository = InMemoryProjectRepository()
        val retrieved = repository.get("NonExistentProject")
        assertNull(retrieved)
    }
}

