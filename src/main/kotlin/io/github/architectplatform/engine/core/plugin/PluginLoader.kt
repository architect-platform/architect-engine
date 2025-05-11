package io.github.architectplatform.engine.core.plugin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.architectplatform.api.context.Context
import io.github.architectplatform.api.plugins.Plugin
import io.github.architectplatform.engine.core.project.application.ProjectDefinition
import jakarta.inject.Singleton
import java.net.URLClassLoader
import java.util.ServiceLoader

@Singleton
class PluginLoader {
	private val objectMapper = ObjectMapper().registerKotlinModule()

	fun load(context: Context): Map<String, Plugin<*>> {
		val definition = context["project"] ?: throw IllegalArgumentException("Project definition not found in context")
		val projectDefinition: ProjectDefinition = objectMapper.convertValue(definition)
		val pluginDefs = projectDefinition.plugins
		println("Loading plugins: ${pluginDefs.joinToString(", ") { it.location }}")
		val plugins = mutableMapOf<String, Plugin<*>>()
		pluginDefs.forEach { plugin ->
			println("Loading plugin ${plugin.location}...")
			val file = PluginDownloader.fetch(plugin.location)
			println("Plugin file downloaded to ${file.absolutePath}")
			val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()), this::class.java.classLoader)
			ServiceLoader.load(Plugin::class.java, classLoader).forEach { loadedPlugin ->
				println("Found plugin: ${loadedPlugin.name}")
				loadedPlugin.context = context
				plugins[loadedPlugin.name] = loadedPlugin
			}
		}
		println("Loaded ${plugins.size} plugins.")
		return plugins
	}
}