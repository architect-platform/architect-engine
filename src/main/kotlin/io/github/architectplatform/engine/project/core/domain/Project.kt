package io.github.architectplatform.engine.project.core.domain

import io.github.architectplatform.api.plugins.ArchitectPlugin
import io.github.architectplatform.api.project.ProjectContext

class Project(
	val name: String,
	val path: String,
	val context: ProjectContext,
	val plugins: List<ArchitectPlugin>,
)

