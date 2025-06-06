package io.github.architectplatform.engine.core.project.domain

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.api.core.project.ProjectContext

class Project(
    val name: String,
    val path: String,
    val context: ProjectContext,
    val plugins: List<ArchitectPlugin<*>>,
)
