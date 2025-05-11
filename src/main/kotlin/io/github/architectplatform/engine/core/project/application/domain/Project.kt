package io.github.architectplatform.engine.core.project.application.domain

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.context.Context
import io.github.architectplatform.api.plugins.Plugin


 class Project (
	val name: String,
	val path: String,
	val description: String,
	var context: Context,
	var commands: Map<String, Command<*>>,
	var plugins: Map<String, Plugin<*>>
 )
