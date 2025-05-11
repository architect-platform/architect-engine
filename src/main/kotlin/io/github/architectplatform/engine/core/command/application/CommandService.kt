package io.github.architectplatform.engine.core.command.application

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.command.CommandResult
import io.github.architectplatform.engine.core.command.application.repositories.CommandRepository
import jakarta.inject.Singleton

@Singleton
class CommandService(
	private val commandRepository: CommandRepository
) {
	fun registerCommand(command: Command<*>) {
		commandRepository.register(command.name, command)
	}

	fun getCommand(name: String): Command<*>? {
		return commandRepository.get(name)
	}

	fun executeCommand(name: String, commandRequest: CommandRequest): CommandResult {
		val command = commandRepository.get(name) ?: throw IllegalArgumentException("Command not found: $name")
		return command.execute(commandRequest)
	}

	fun getAllCommands(): List<Command<*>> {
		return commandRepository.getAll()
	}
}