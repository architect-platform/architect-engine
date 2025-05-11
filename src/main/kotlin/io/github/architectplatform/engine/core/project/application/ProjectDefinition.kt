package io.github.architectplatform.engine.core.project.application

import io.github.architectplatform.engine.core.plugin.PluginDefinition
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ProjectDefinition (
	val name: String,
	val description: String = "",
	val plugins: List<PluginDefinition> = emptyList(),
)