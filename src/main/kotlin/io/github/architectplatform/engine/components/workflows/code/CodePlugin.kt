package io.github.architectplatform.engine.components.workflows.code

import io.github.architectplatform.api.plugins.WorkflowPlugin
import io.github.architectplatform.api.workflows.code.CodeWorkflow
import jakarta.inject.Singleton

// ------------
// Asset Plugins: register subphases; general executor will group by parent phase
// ------------
@Singleton
class CodePlugin : WorkflowPlugin(
	id = "code-phases",
	phases = CodeWorkflow.entries,
)

