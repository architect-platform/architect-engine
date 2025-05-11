package io.github.architectplatform.engine.core.command.application

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.engine.core.command.application.repositories.CommandRepository
import io.micronaut.context.annotation.Context

/**
 * A class that loads commands into the command registry.
 *
 * @param commandRepository The command registry to load commands into.
 * @param commands The list of commands to be loaded.
 */
@Context
class CommandsLoader(
	private val commandRepository: CommandRepository,
	private val commands: List<Command<*>>
) {
	init {
		commands.forEach { command ->
			commandRepository.register(command.name, command)
		}
	}
}