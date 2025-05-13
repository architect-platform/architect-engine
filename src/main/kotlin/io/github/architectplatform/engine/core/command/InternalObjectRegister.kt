package io.github.architectplatform.engine.core.command

import io.github.architectplatform.api.command.Command
import io.micronaut.context.annotation.Context

@Context
class InternalObjectRegister(private val commandRegistry: CommandRegistry, private val commands: List<Command<*>>) {
	init {
		commands.forEach { command ->
			commandRegistry.registerCommand(command)
		}
	}
}