package io.github.architectplatform.engine.core.plugin.app

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.api.core.project.ProjectContext

interface PluginLoader {
  fun load(context: ProjectContext): List<ArchitectPlugin<*>>
}
