package io.github.architectplatform.engine.plugins.workflows.core

import io.github.architectplatform.api.core.plugins.phase.WorkflowPlugin
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