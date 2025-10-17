package io.github.architectplatform.engine.plugins.workflows.hooks.tasks

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class HooksInstallTaskTest {
    @Test
    fun testHooksInstallTaskExecution() {
        val task = HooksInstallTask(mock())
        assertDoesNotThrow { task.execute() }
    }
}

