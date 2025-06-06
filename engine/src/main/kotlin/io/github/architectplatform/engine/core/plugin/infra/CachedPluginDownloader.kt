package io.github.architectplatform.engine.core.plugin.infra

import io.github.architectplatform.engine.core.plugin.app.PluginDownloader
import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Singleton
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Singleton
@ExecuteOn(TaskExecutors.BLOCKING)
class CachedPluginDownloader(private val httpClient: HttpClient) : PluginDownloader {
  private val cache =
      Paths.get(System.getProperty("user.home"), ".architect-engine", "plugins").also {
        Files.createDirectories(it)
      }

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
    val request = HttpRequest.GET<Any>(url)
    val response = httpClient.toBlocking().exchange(request, ByteArray::class.java)
    val body = response.body() ?: error("Failed to download plugin: empty response body")
    Files.write(target.toPath(), body)
    println("Plugin downloaded to ${target.absolutePath}")
    return target
  }
}
