package io.github.architectplatform.engine.core.command.application

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.context.Context
import jakarta.inject.Singleton

@Singleton
class CommandLoader(
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

