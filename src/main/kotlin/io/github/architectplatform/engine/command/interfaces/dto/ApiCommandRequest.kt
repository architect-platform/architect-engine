package io.github.architectplatform.engine.command.interfaces.dto

import io.micronaut.serde.annotation.Serdeable
import java.util.Collections.emptyList

@Serdeable
data class ApiCommandRequest(
	val name: String,
	val args: List<String> = emptyList()
)

