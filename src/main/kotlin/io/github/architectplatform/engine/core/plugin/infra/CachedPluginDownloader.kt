package io.github.architectplatform.engine.core.plugin.infra

import io.github.architectplatform.engine.core.plugin.application.PluginDownloader
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

object CachedPluginDownloader : PluginDownloader {
	private val cache = Paths.get(System.getProperty("user.home"), ".architect-engine", "plugins")
		.also { Files.createDirectories(it) }

	override fun download(url: String): File {
		val jarName = "${url.hashCode()}.jar"
		val target = cache.resolve(jarName).toFile()
		if (!target.exists()) {
			println("Downloading plugin from $url...")
			URL(url).openStream().use { inp ->
				target.outputStream().use { out -> inp.copyTo(out) }
			}
		} else {
			println("Plugin already downloaded at ${target.absolutePath}")
		}
		return target
	}
}