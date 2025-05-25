package io.github.architectplatform.engine.core.plugin.infra

import io.github.architectplatform.engine.core.plugin.app.PluginDownloader
import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

@Singleton
class CachedPluginDownloader : io.github.architectplatform.engine.core.plugin.app.PluginDownloader {
	private val cache = Paths.get(System.getProperty("user.home"), ".architect-engine", "plugins")
		.also { Files.createDirectories(it) }

	@Property(name = "architect.engine.plugin.cache.enabled", defaultValue = "true")
	var cacheEnabled: Boolean = true

	override fun download(url: String): File {
		val jarName = "${url.hashCode()}.jar"
		val target = cache.resolve(jarName).toFile()
		if (target.exists() && cacheEnabled) {
			println("Plugin already downloaded at ${target.absolutePath}")
			return target
		}
		println("Downloading plugin from $url...")
		URL(url).openStream().use { inp ->
			target.outputStream().use { out -> inp.copyTo(out) }
		}
		return target
	}
}