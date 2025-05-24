package io.github.architectplatform.engine.components.workflows.hooks

import io.github.architectplatform.api.core.plugins.WorkflowPlugin
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.api.components.workflows.hooks.HooksWorkflow
import io.github.architectplatform.engine.components.workflows.hooks.tasks.HooksInstallTask
import io.github.architectplatform.engine.components.workflows.hooks.tasks.HooksVerifyTask
import jakarta.inject.Singleton


// ------------
// HooksPlugin
// ------------
@Singleton
class HooksPlugin : WorkflowPlugin(
	id = "hooks-phases",
	phases = HooksWorkflow.entries
) {
	override fun register(registry: TaskRegistry) {
		super.register(registry)
		registry.add(HooksInstallTask())
		registry.add(HooksVerifyTask())
	}
}

