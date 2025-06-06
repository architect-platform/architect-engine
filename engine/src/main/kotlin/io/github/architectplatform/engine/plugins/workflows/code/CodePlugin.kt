package io.github.architectplatform.engine.plugins.workflows.code

import io.github.architectplatform.api.core.plugins.phase.WorkflowPlugin
import io.github.architectplatform.api.components.workflows.code.CodeWorkflow
import jakarta.inject.Singleton

// ------------
// Asset Plugins: register subphases; general executor will group by parent phase
// ------------
@Singleton
class CodePlugin : WorkflowPlugin(
	id = "code-phases",
	phases = CodeWorkflow.entries,
)

