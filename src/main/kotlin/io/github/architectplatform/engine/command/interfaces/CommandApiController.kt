package io.github.architectplatform.engine.command.interfaces

import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.engine.command.interfaces.dto.ApiCommandResponse
import io.github.architectplatform.engine.command.application.CommandRegistry
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

@Controller("/api/commands")
class CommandApiController(
	private val commandRegistry: CommandRegistry
) {

	@Get
	fun getAll(): List<String> {
		return commandRegistry.getAllCommandNames()
	}

	@Get("/{name}")
	fun getCommand(name: String): CommandDTO? {
		val command = commandRegistry.get(name)
			?: return null

		return CommandDTO(
			name = command.name,
			description = command.description,
			usage = command.usage,
		)
	}

	@Post("/{name}")
	fun executeCommand(@PathVariable name: String, @Body args: List<String>): ApiCommandResponse {
		println("Calling command: $name")

		val commandToExecute = commandRegistry.get(name)
			?: return ApiCommandResponse.Failure("Command not found")

		try {
			val result = commandToExecute.execute(CommandRequest(args))
			return ApiCommandResponse.Success(result.output)
		} catch (e: Exception) {
			return ApiCommandResponse.Failure("Error executing command: ${e.message}")
		}
	}

	companion object {
		data class CommandDTO(
			val name: String,
			val description: String,
			val usage: String,
			val subcommands: List<CommandDTO> = emptyList()
		)
	}
}