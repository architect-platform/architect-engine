package io.github.architectplatform.engine.core.plugin.domain.events

import io.github.architectplatform.engine.domain.events.ArchitectEvent

data class PluginDownloadStarted(val pluginId: String, override val message: String = "") :
	ArchitectEvent