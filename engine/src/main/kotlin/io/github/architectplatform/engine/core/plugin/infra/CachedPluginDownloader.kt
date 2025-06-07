package io.github.architectplatform.engine.core.plugin.infra

import io.github.architectplatform.engine.core.plugin.app.PluginDownloader
import io.github.architectplatform.engine.core.plugin.domain.events.PluginDownloadCompleted
import io.github.architectplatform.engine.core.plugin.domain.events.PluginDownloadSkipped
import io.github.architectplatform.engine.core.plugin.domain.events.PluginDownloadStarted
import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.micronaut.context.annotation.Property
import io.micronaut.context.event.ApplicationEventPublisher
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
class CachedPluginDownloader(
    private val httpClient: HttpClient,
    private val eventPublisher: ApplicationEventPublisher<ArchitectEvent>,
) : PluginDownloader {
  private val cache =
      Paths.get(System.getProperty("user.home"), ".architect-engine", "plugins").also {
        Files.createDirectories(it)
      }

  @Property(name = "architect.engine.plugin.cache.enabled", defaultValue = "true")
  var cacheEnabled: Boolean = true

  override fun download(url: String): File {
    val jarName = "${url.hashCode()}.jar"
    val target = cache.resolve(jarName).toFile()
    if (cacheEnabled && target.exists()) {
      eventPublisher.publishEvent(
          PluginDownloadSkipped(pluginId = jarName, message = "Plugin already cached"))
      return target
    }
    eventPublisher.publishEvent(
        PluginDownloadStarted(pluginId = jarName, message = "Downloading plugin from $url"))
    val request = HttpRequest.GET<Any>(url)
    val response = httpClient.toBlocking().exchange(request, ByteArray::class.java)
    val body = response.body() ?: error("Failed to download plugin: empty response body")
    Files.write(target.toPath(), body)

    eventPublisher.publishEvent(
        PluginDownloadCompleted(pluginId = jarName, message = "Plugin downloaded successfully"))
    return target
  }
}
