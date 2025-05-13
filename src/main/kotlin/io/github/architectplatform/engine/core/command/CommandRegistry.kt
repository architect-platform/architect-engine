package io.github.architectplatform.engine.core.command

import io.github.architectplatform.api.command.Command
import jakarta.inject.Singleton

@Singleton
class CommandRegistry {
	private val commands = mutableMapOf<String, Command<*>>()

	fun registerCommand(command: Command<*>) {
		commands[command.name] = command
	}

	fun getCommand(name: String): Command<*>? {
		return commands[name]
	}

	inline fun <reified T> getByType(): List<T> {
		return getAllCommands().filterIsInstance<T>()
	}

	fun getAllCommands(): List<Command<*>> {
		return commands.values.toList()
	}
}

