package io.github.architectplatform.engine.core.plugin.app

import io.github.architectplatform.api.plugins.ArchitectPlugin
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
class PluginLoaderFactory {
	@Singleton
	fun loader(
		downloader: io.github.architectplatform.engine.core.plugin.app.PluginDownloader,
		internalPlugins: List<ArchitectPlugin> = emptyList(),
	): io.github.architectplatform.engine.core.plugin.app.PluginLoader =
		io.github.architectplatform.engine.core.plugin.app.ProjectPluginLoader(
			io.github.architectplatform.engine.core.plugin.app.SpiPluginLoader(),
			downloader,
			internalPlugins
		)
}