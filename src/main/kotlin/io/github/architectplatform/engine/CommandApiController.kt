package io.github.architectplatform.engine

import io.github.architectplatform.api.command.CommandRequest
import io.github.architectplatform.api.interfaces.ApiCommandRequest
import io.github.architectplatform.api.interfaces.ApiCommandResponse
import io.github.architectplatform.engine.command.CommandRegistry
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/api/command")
class CommandApiController(
	private val commandRegistry: CommandRegistry
) {
	@Post
	fun executeCommand(@Body command: ApiCommandRequest): ApiCommandResponse {
		println("Calling command: ${command.name}")

		val commandToExecute = commandRegistry.get(command.name)
			?: return ApiCommandResponse.Failure( "Command not found")

		try {
			val result = commandToExecute.execute(CommandRequest(command.args))
			return ApiCommandResponse.Success(result.output)
		} catch (e: Exception) {
			return ApiCommandResponse.Failure("Error executing command: ${e.message}")
		}

	}
}