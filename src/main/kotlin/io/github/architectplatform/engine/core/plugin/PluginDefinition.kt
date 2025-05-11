package io.github.architectplatform.engine.core.plugin

data class PluginDefinition(
	val location: String,
	val config: Map<String, Any>? = emptyMap()
)