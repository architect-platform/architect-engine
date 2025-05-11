package io.github.architectplatform.engine.core.plugin

import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

object PluginDownloader {
	private val cache = Paths.get(System.getProperty("user.home"), ".architect-engine", "plugins")
		.also { Files.createDirectories(it) }

	fun fetch(location: String): File {
		val jarName = "${location.hashCode()}.jar"
		val target = cache.resolve(jarName).toFile()
		if (!target.exists()) {
			println("Downloading plugin from $location...")
			URL(location).openStream().use { inp ->
				target.outputStream().use { out -> inp.copyTo(out) }
			}
		} else {
			println("Plugin already downloaded at ${target.absolutePath}")
		}
		return target
	}
}