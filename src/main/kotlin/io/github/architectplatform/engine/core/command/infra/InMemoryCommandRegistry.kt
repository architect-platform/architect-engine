package io.github.architectplatform.engine.core.command.infra

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.engine.core.command.application.CommandRegistry
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

	override fun getAllCommandNames(): List<String> {
		return commands.keys.toList()
	}
}