package io.github.architectplatform.engine.core.plugin.domain.events

import io.github.architectplatform.engine.domain.events.ArchitectEvent

data class PluginLoaded(
    val pluginId: String,
    val projectId: String,
    override val message: String = ""
) : ArchitectEvent

