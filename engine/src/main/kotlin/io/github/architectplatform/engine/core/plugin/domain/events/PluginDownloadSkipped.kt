package io.github.architectplatform.engine.core.plugin.domain.events

import io.github.architectplatform.engine.domain.events.ArchitectEvent

data class PluginDownloadSkipped(val pluginId: String, override val message: String = "") :
    ArchitectEvent
