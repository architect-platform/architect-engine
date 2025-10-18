package io.github.architectplatform.engine.plugins.workflows.hooks

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.engine.core.plugin.app.InternalPluginProvider
import jakarta.inject.Singleton

@Singleton
class HooksPluginProvider : InternalPluginProvider {
  override fun getPlugin(): ArchitectPlugin<*> = HooksPlugin()
}
