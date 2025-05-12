package io.github.architectplatform.engine.core.command

import io.github.architectplatform.api.command.Command
import jakarta.inject.Singleton

@Singleton
class CommonCommandLoader(
	private val internalCommands: List<Command<*>>,
) {
	fun getCommands(): Map<String, Command<*>> {
		val commands = mutableMapOf<String, Command<*>>()
		internalCommands.forEach { command ->
			commands[command.name] = command
		}
		return commands
	}
}

