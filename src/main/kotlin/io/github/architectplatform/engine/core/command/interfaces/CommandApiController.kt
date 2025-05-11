package io.github.architectplatform.engine.core.command.interfaces

import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.engine.core.command.interfaces.dto.ApiCommandResponse
import io.github.architectplatform.engine.core.command.application.CommandRegistry
import io.github.architectplatform.engine.core.command.interfaces.dto.ApiCommandDTO
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
		println("Getting all commands")
		return commandRegistry.getAllCommandNames()
	}

	@Get("/{name}")
	fun getCommand(@PathVariable name: String): ApiCommandDTO? {
		println("Getting command: $name")
		val command = commandRegistry.get(name)
			?: return null

		return ApiCommandDTO(
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

}