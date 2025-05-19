package io.github.architectplatform.engine.plugin

data class PluginDefinition(
	val location: String,
	val config: Map<String, Any>? = emptyMap(),
)