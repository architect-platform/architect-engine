package io.github.architectplatform.engine.plugin.app

import io.github.architectplatform.api.plugins.ArchitectPlugin
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
class PluginLoaderFactory {
	@Singleton
	fun loader(
		downloader: PluginDownloader,
		internalPlugins: List<ArchitectPlugin> = emptyList(),
	): PluginLoader = ProjectPluginLoader(SpiPluginLoader(), downloader, internalPlugins)
}