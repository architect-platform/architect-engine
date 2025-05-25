package io.github.architectplatform.engine.components.workflows.core

import io.github.architectplatform.api.core.plugins.WorkflowPlugin
import io.github.architectplatform.api.components.workflows.core.CoreWorkflow
import jakarta.inject.Singleton


// ------------
// CorePlugin
// ------------
@Singleton
class CorePlugin : WorkflowPlugin(
	id = "core-phases",
	phases = CoreWorkflow.entries
)