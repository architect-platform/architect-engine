package io.github.architectplatform.engine.command.interfaces

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/api/health")
class HealthApiController {
	@Get
	fun health(): String {
		println("Health check endpoint called")
		return "OK"
	}
}