package io.github.architectplatform.engine.domain.events

typealias PluginId = String

interface PluginEvent {
  val pluginId: PluginId
  val pluginEventType: PluginEventType
}

enum class PluginEventType {
  DOWNLOAD_STARTED,
  DOWNLOAD_COMPLETED,
  DOWNLOAD_FAILED,
  DOWNLOAD_SKIPPED,
  LOADED,
}
