package io.github.architectplatform.engine.core.project.app.ports

import io.github.architectplatform.api.core.project.Config

interface ConfigParser {
  fun parse(context: String): Config
}
