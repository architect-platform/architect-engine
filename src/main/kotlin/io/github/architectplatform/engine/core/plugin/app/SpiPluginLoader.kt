package io.github.architectplatform.engine.core.plugin.app

import io.github.architectplatform.api.plugins.ArchitectPlugin
import java.util.*

class SpiPluginLoader {
	/** Discover plugins in the given ClassLoader via `META-INF/services`. */
	fun loadFrom(classLoader: ClassLoader): List<ArchitectPlugin<*>> =
		ServiceLoader.load(ArchitectPlugin::class.java, classLoader).toList()
}