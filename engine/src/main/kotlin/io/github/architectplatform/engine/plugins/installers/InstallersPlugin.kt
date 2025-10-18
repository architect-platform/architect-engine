package io.github.architectplatform.engine.plugins.installers

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.engine.plugins.installers.context.InstallersContext
import io.github.architectplatform.engine.plugins.installers.tasks.CopyInstallerTask

class InstallersPlugin : ArchitectPlugin<InstallersContext> {
  override val id: String = "installers-plugin"
  override val contextKey: String = "installers"
  override val ctxClass: Class<InstallersContext> = InstallersContext::class.java
  override var context: InstallersContext = InstallersContext()

  override fun register(registry: TaskRegistry) {
    registry.add(CopyInstallerTask(context))
  }
}
