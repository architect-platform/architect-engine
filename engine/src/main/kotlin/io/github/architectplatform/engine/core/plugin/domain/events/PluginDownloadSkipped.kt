package io.github.architectplatform.engine.core.plugin.domain.events

import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class PluginDownloadSkipped(
    val pluginId: String,
    override val message: String = "",
    override val success: Boolean = true
) : ArchitectEvent
