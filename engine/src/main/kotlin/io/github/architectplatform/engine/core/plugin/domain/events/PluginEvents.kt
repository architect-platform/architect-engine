package io.github.architectplatform.engine.core.plugin.domain.events

import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.PluginEvent
import io.github.architectplatform.engine.domain.events.PluginEventType
import io.github.architectplatform.engine.domain.events.PluginId
import io.micronaut.serde.annotation.Serdeable

object PluginEvents {

  @Serdeable
  data class PluginEventDTO(
      override val pluginEventType: PluginEventType,
      override val pluginId: PluginId,
  ) : PluginEvent

  fun pluginDownloadCompleted(
      pluginId: PluginId,
  ): ArchitectEvent<PluginEventDTO> =
      ArchitectEventDTO(
          "plugin.download.completed",
          PluginEventDTO(
              pluginEventType = PluginEventType.DOWNLOAD_COMPLETED,
              pluginId = pluginId,
          ))

  fun pluginDownloadSkipped(
      pluginId: PluginId,
  ): ArchitectEvent<PluginEventDTO> =
      ArchitectEventDTO(
          "plugin.download.skipped",
          PluginEventDTO(
              pluginEventType = PluginEventType.DOWNLOAD_SKIPPED,
              pluginId = pluginId,
          ),
      )

  fun pluginDownloadStarted(
      pluginId: PluginId,
  ): ArchitectEvent<PluginEventDTO> =
      ArchitectEventDTO(
          "plugin.download.started",
          PluginEventDTO(
              pluginEventType = PluginEventType.DOWNLOAD_STARTED,
              pluginId = pluginId,
          ),
      )

  fun pluginDownloadFailed(
      pluginId: PluginId,
  ): ArchitectEvent<PluginEventDTO> =
      ArchitectEventDTO(
          "plugin.download.failed",
          PluginEventDTO(
              pluginEventType = PluginEventType.DOWNLOAD_FAILED,
              pluginId = pluginId,
          ))

  fun pluginLoaded(
      pluginId: PluginId,
  ): ArchitectEvent<PluginEventDTO> =
      ArchitectEventDTO(
          "plugin.loaded",
          PluginEventDTO(
              pluginEventType = PluginEventType.LOADED,
              pluginId = pluginId,
          ))
}
