package io.github.architectplatform.engine.project.context.application

import io.github.architectplatform.api.context.Config
import io.github.architectplatform.engine.project.context.application.ports.ConfigParser
import jakarta.inject.Singleton
import java.io.File

@Singleton
class ConfigLoader(private val configParser: ConfigParser) {

	fun load(path: String): Config {
		val yamlContext = getExternalConfiguration(path)
		return configParser.parse(yamlContext)
	}

	private fun getExternalConfiguration(projectPath: String = "."): String {
		val configuration = StringBuilder()

		// 1) Load the main architect.yml/yaml if present
		val rootYaml = File(projectPath, "architect.yml").takeIf { it.exists() }
			?: File(projectPath, "architect.yaml").takeIf { it.exists() }
		if (rootYaml != null) {
			try {
				configuration.append(rootYaml.readText()).append("\n")
			} catch (e: Exception) {
				println("Failed to read ${rootYaml.name}, skipping. Reason: ${e.message}")
			}
		}

		// 2) Load all files in .architect folder
		val contextDir = File(projectPath, ".architect")
		if (!contextDir.exists() || !contextDir.isDirectory) {
			return configuration.toString()
		}

		val yamlFiles = contextDir.listFiles { file ->
			file.isFile && (file.extension.equals("yml", true) || file.extension.equals("yaml", true))
		}?.sortedBy { it.name }  // optional: deterministic order
			?: emptyList()

		if (yamlFiles.isEmpty()) {
			return configuration.toString()
		}

		yamlFiles.forEach { file ->
			try {
				configuration.append(file.readText()).append("\n")
			} catch (e: Exception) {
				println("Failed to read file: ${file.name}, skipping. Reason: ${e.message}")
			}
		}

		return configuration.toString()
	}
}