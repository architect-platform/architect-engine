package io.github.architectplatform.engine.command.application

import io.github.architectplatform.api.command.Command
import io.micronaut.context.annotation.Context

/**
 * A class that loads commands into the command registry.
 *
 * @param commandRegistry The command registry to load commands into.
 * @param commands The list of commands to be loaded.
 */
@Context
class CommandsLoader(
	private val commandRegistry: CommandRegistry,
	private val commands: List<Command<*>>
) {
	init {
		commands.forEach { command ->
			commandRegistry.register(command)
		}
	}
}