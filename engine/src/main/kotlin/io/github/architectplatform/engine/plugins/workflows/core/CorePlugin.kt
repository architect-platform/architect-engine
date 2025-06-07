package io.github.architectplatform.engine.plugins.workflows.core

import io.github.architectplatform.api.components.workflows.core.CoreWorkflow
import io.github.architectplatform.engine.plugins.workflows.WorkflowPlugin
import jakarta.inject.Singleton

// ------------
// CorePlugin
// ------------
@Singleton class CorePlugin : WorkflowPlugin(id = "core-phases", phases = CoreWorkflow.entries)
