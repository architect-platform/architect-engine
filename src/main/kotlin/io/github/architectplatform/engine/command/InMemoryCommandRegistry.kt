package io.github.architectplatform.engine.command

import io.github.architectplatform.api.command.Command
import jakarta.inject.Singleton

@Singleton
class InMemoryCommandRegistry : CommandRegistry {
	private val commands = mutableMapOf<String, Command<*>>()

	override fun register(command: Command<*>) {
		if (commands.containsKey(command.name)) {
			throw IllegalArgumentException("Command with name ${command.name} already exists.")
		}
		commands[command.name] = command
	}

	override fun get(name: String): Command<*>? {
		return commands[name]
	}
}