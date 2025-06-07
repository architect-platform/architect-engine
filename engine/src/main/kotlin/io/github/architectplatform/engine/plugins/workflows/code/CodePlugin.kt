package io.github.architectplatform.engine.plugins.workflows.code

import io.github.architectplatform.api.components.workflows.code.CodeWorkflow
import io.github.architectplatform.engine.plugins.workflows.WorkflowPlugin
import jakarta.inject.Singleton

// ------------
// Asset Plugins: register subphases; general executor will group by parent phase
// ------------
@Singleton
class CodePlugin :
    WorkflowPlugin(
        id = "code-phases",
        phases = CodeWorkflow.entries,
    )
