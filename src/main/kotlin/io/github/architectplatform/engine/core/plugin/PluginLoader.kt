package io.github.architectplatform.engine.core.plugin

import io.github.architectplatform.api.plugins.Plugin
import jakarta.inject.Singleton
import java.net.URLClassLoader
import java.util.ServiceLoader

@Singleton
class PluginLoader {

	fun load(pluginDefs: List<PluginDefinition>): Map<String, Plugin<*>> {
		println("Loading plugins: ${pluginDefs.joinToString(", ") { it.location }}")
		val plugins = mutableMapOf<String, Plugin<*>>()
		pluginDefs.forEach { plugin ->
			println("Loading plugin ${plugin.location}...")
			val file = PluginDownloader.fetch(plugin.location)
			println("Plugin file downloaded to ${file.absolutePath}")
			val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()), this::class.java.classLoader)
			ServiceLoader.load(Plugin::class.java, classLoader).forEach { loadedPlugin ->
				println("Found plugin: ${loadedPlugin.name}")
				plugins[loadedPlugin.name] = loadedPlugin
			}
		}
		println("Loaded ${plugins.size} plugins.")
		return plugins
	}
}