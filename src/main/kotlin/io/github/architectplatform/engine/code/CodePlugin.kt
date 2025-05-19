package io.github.architectplatform.engine.code

import io.github.architectplatform.api.assets.code.CodePhases
import io.github.architectplatform.api.plugins.ArchitectPlugin
import io.github.architectplatform.api.tasks.TaskRegistry
import jakarta.inject.Singleton

// ------------
// Asset Plugins: register subphases; general executor will group by parent phase
// ------------
@Singleton
class CodePlugin : ArchitectPlugin {
	override val id = "code-plugin"
	override fun register(registry: TaskRegistry) {
		CodePhases.entries.forEach { phase -> registry.add(CodePhaseTaskExecutor(phase, registry)) }
	}
}

