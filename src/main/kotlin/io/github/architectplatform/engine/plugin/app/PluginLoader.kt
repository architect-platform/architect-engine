package io.github.architectplatform.engine.plugin.app

import io.github.architectplatform.api.plugins.ArchitectPlugin
import io.github.architectplatform.api.project.ProjectContext

interface PluginLoader {
	fun load(context: ProjectContext): List<ArchitectPlugin>
}

