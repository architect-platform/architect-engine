package io.github.architectplatform.engine.project.context.application.ports

import io.github.architectplatform.api.context.Config

interface ConfigParser {
	fun parse(context: String): Config
}

