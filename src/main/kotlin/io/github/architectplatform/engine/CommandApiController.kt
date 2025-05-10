package io.github.architectplatform.engine

import io.github.architectplatform.api.interfaces.ApiCommandRequest
import io.github.architectplatform.api.interfaces.ApiCommandResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/api/command")
class CommandApiController {
	@Post
	fun executeCommand(command: ApiCommandRequest): ApiCommandResponse {
		println("Calling command: ${command.name}")
		// Here you would implement the logic to execute the command
		return ApiCommandResponse(true, "Command executed successfully")
	}
}