package io.github.architectplatform.engine.plugins.installers.tasks

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class CopyInstallerTaskTest {
    @Test
    fun testTaskExecution() {
        val context = mock<InstallersContext>()
        val task = CopyInstallerTask(context)
        assertDoesNotThrow { task.execute() }
    }
}

