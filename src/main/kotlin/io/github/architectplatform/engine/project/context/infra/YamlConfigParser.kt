package io.github.architectplatform.engine.project.context.infra

import io.github.architectplatform.api.context.Config
import io.github.architectplatform.engine.project.context.application.ports.ConfigParser
import jakarta.inject.Singleton
import org.yaml.snakeyaml.Yaml

@Singleton
class YamlConfigParser : ConfigParser {

	private val yaml = Yaml()

	override fun parse(context: String): Config {
		return yaml.load(context) as Map<String, Any>
	}
}