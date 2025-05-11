package io.github.architectplatform.engine.core.context.application.ports

import io.github.architectplatform.api.context.Context

interface ContextParser {
	fun parse(context: String): Context
}

