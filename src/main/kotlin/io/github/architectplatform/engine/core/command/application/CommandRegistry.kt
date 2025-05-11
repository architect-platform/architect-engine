package io.github.architectplatform.engine.core.command.application

import io.github.architectplatform.api.command.Command

/**
 * Registry for commands.
 * This interface allows for the registration and retrieval of commands by their name.
 * It is used to manage the available commands in the system.
 */
interface CommandRegistry {
	/**
	 * Registers a command with the given name and implementation.
	 *
	 * @param name The name of the command.
	 * @param command The command implementation.
	 */
	fun register(command: Command<*>)

	/**
	 * Retrieves a command by its name.
	 *
	 * @param name The name of the command.
	 * @return The command implementation, or null if not found.
	 */
	fun get(name: String): Command<*>?


	/**
	 * Retrieves all registered command names.
	 *
	 * @return A list of all command names.
	 */
	fun getAllCommandNames(): List<String>
}


