package io.github.architectplatform.engine.core.plugin.app

import java.io.File

interface PluginDownloader {
  fun download(url: String): File
}
