package io.github.architectplatform.engine.plugins.commits

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.engine.core.plugin.app.CommonPlugin
import jakarta.inject.Singleton

@Singleton
class CommitsPluginProvider : CommonPlugin {
  override fun getPlugin(): ArchitectPlugin<*> = CommitsPlugin()
}
