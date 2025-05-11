package io.github.architectplatform.engine.core.plugin.application

import java.io.File

interface PluginDownloader {
	fun download(url: String): File
}