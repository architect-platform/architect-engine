package io.github.architectplatform.engine.plugins.installers

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.engine.core.plugin.app.CommonPlugin
import jakarta.inject.Singleton

@Singleton
class InstallersPluginProvider : CommonPlugin {
  override fun getPlugin(): ArchitectPlugin<*> = InstallersPlugin()
}
