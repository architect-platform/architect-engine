package io.github.architectplatform.engine.project.app.ports

import io.github.architectplatform.api.project.Config

interface ConfigParser {
	fun parse(context: String): Config
}

