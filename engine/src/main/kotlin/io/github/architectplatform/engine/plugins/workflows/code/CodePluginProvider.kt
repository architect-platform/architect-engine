package io.github.architectplatform.engine.plugins.workflows.code

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.engine.core.plugin.app.CommonPlugin
import jakarta.inject.Singleton

@Singleton
class CodePluginProvider : CommonPlugin {
  override fun getPlugin(): ArchitectPlugin<*> = CodePlugin()
}
