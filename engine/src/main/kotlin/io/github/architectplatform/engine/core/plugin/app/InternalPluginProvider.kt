package io.github.architectplatform.engine.core.plugin.app

import io.github.architectplatform.api.core.plugins.ArchitectPlugin

interface InternalPluginProvider {
  fun getPlugin(): ArchitectPlugin<*>
}
