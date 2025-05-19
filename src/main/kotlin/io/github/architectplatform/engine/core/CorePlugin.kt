package io.github.architectplatform.engine.core

import io.github.architectplatform.api.core.CorePhases
import io.github.architectplatform.api.plugins.ArchitectPlugin
import io.github.architectplatform.api.tasks.TaskRegistry
import jakarta.inject.Singleton


// ------------
// CorePlugin
// ------------
@Singleton
class CorePlugin : ArchitectPlugin {
	override val id = "core-plugin"
	override fun register(registry: TaskRegistry) {
		CorePhases.entries.forEach { phase ->
			registry.add(CorePhaseExecutorTask(phase, registry))
		}
	}
}

