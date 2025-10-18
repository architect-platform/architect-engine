package io.github.architectplatform.engine.plugins.workflows.code

import io.github.architectplatform.api.components.workflows.code.CodeWorkflow
import io.github.architectplatform.engine.plugins.workflows.WorkflowPlugin

// ------------
// Asset Plugins: register subphases; general executor will group by parent phase
// ------------
class CodePlugin :
    WorkflowPlugin(
        id = "code-phases",
        phases = CodeWorkflow.entries,
    )

