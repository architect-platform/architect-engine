package io.github.architectplatform.engine.core.plugin.app

import io.github.architectplatform.api.plugins.ArchitectPlugin
import io.github.architectplatform.api.project.ProjectContext
import io.github.architectplatform.api.project.getKey
import java.net.URLClassLoader

class ProjectPluginLoader(
	private val spiLoader: SpiPluginLoader,
	private val downloader: PluginDownloader,
	private val internalPlugins: List<ArchitectPlugin<*>>,
) : PluginLoader {

	override fun load(context: ProjectContext): List<ArchitectPlugin<*>> {
		val enabled = mutableListOf<ArchitectPlugin<*>>()
		// 1) Always include CorePlugin
		enabled += internalPlugins

		// 2) Download & load each project-declared plugin JAR
		context.config.getKey<List<String>>("plugins")?.forEach { url ->
			val jar = downloader.download(url)
			val loader = URLClassLoader(arrayOf(jar.toURI().toURL()), this::class.java.classLoader)
			enabled += spiLoader.loadFrom(loader)
		}


		return enabled
	}
}