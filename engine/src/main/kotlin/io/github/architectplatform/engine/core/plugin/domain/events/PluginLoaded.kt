package io.github.architectplatform.engine.core.plugin.domain.events

import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class PluginLoaded(
    val pluginId: String,
    val projectId: String,
    override val message: String = "",
    override val success: Boolean = true
) : ArchitectEvent
