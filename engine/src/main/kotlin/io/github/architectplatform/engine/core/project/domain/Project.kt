package io.github.architectplatform.engine.core.project.domain

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.engine.core.tasks.infrastructure.InMemoryTaskRegistry

class Project(
    val name: String,
    val path: String,
    val context: ProjectContext,
    val plugins: List<ArchitectPlugin<*>>,
    val subProjects: List<Project> = emptyList(),
    val taskRegistry: TaskRegistry = InMemoryTaskRegistry()
)
