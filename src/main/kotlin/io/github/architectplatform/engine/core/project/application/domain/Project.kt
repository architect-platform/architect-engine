package io.github.architectplatform.engine.core.project.application.domain

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.context.Context
import io.github.architectplatform.api.plugins.Plugin


class Project(
	val name: String,
	val path: String,
	val description: String,
	var context: Context,
	var commands: Map<String, Command<*>>,
	var plugins: Map<String, Plugin>,
) {
	/**
	 * Get a command by its name.
	 * If the command is not found, it will check if it is a plugin command.
	 */
	fun getCommand(name: String): Command<*>? {
		if (commands.containsKey(name)) {
			return commands[name]
		}

		// If the command is not found, check if it is a plugin command
		for (plugin in plugins.values) {
			val command = plugin.getCommands().find { it.name == name }
			if (command != null) {
				return command
			}
		}

		return null
	}
	
}

