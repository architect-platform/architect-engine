package io.github.architectplatform.engine.commons.phases.tasks

import io.github.architectplatform.api.tasks.init.InitTask
import io.github.architectplatform.api.tasks.init.InitTaskResult
import jakarta.inject.Singleton


@Singleton
class HooksInitTask : InitTask() {
	override val name: String = "hooks"
	override fun executeTask(path: String): InitTaskResult {
		println("Executing hooks init task")
		// Implement the logic for initializing hooks here
		return InitTaskResult.success("Hooks initialized successfully")
	}
}