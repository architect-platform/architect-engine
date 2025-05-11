package io.github.architectplatform.engine.core.context.infra

import io.github.architectplatform.api.context.Context
import io.github.architectplatform.engine.core.context.application.ports.ContextParser
import jakarta.inject.Singleton
import org.yaml.snakeyaml.Yaml

@Singleton
class YamlContextParser: ContextParser {

	private val yaml = Yaml()

	override fun parse(context: String): Context {
		return yaml.load(context) as Map<String, Any>
	}
}