package io.github.architectplatform.engine.plugins.workflows.hooks.tasks

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class HooksVerifyTaskTest {
    @Test
    fun testHooksVerifyTaskExecution() {
        val task = HooksVerifyTask(mock())
        assertDoesNotThrow { task.execute() }
    }
}

