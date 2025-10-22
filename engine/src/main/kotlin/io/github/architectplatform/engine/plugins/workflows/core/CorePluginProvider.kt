package io.github.architectplatform.engine.plugins.workflows.core

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.engine.core.plugin.app.CommonPlugin
import jakarta.inject.Singleton

@Singleton
class CorePluginProvider : CommonPlugin {
  override fun getPlugin(): ArchitectPlugin<*> = CorePlugin()
}
