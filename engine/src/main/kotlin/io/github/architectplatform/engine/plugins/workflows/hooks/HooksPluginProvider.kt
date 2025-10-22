package io.github.architectplatform.engine.plugins.workflows.hooks

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.engine.core.plugin.app.CommonPlugin
import jakarta.inject.Singleton

@Singleton
class HooksPluginProvider : CommonPlugin {
  override fun getPlugin(): ArchitectPlugin<*> = HooksPlugin()
}
