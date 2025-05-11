package io.github.architectplatform.engine.core.context.application

import io.github.architectplatform.api.context.Context
import io.github.architectplatform.engine.core.context.application.ports.ContextParser
import jakarta.inject.Singleton
import java.io.File

@Singleton
class ContextLoader(private val contextParser: ContextParser) {

	fun getContext(path: String): Context {
		val yamlContext = getExternalConfiguration(path)
		println("Loaded configuration: $yamlContext")
		return contextParser.parse(yamlContext)
	}

	private fun getExternalConfiguration(projectPath: String = "."): String {
		val configuration = StringBuilder()

		// 1) Load the main architect.yml/yaml if present
		val rootYaml = File(projectPath, "architect.yml").takeIf { it.exists() }
			?: File(projectPath, "architect.yaml").takeIf { it.exists() }
		if (rootYaml != null) {
			try {
				println("Loading root configuration: ${rootYaml.name}")
				configuration.append(rootYaml.readText()).append("\n")
			} catch (e: Exception) {
				println("Failed to read ${rootYaml.name}, skipping. Reason: ${e.message}")
			}
		} else {
			println("No root architect.yml/.yaml found")
		}

		// 2) Load all files in .architect folder
		val contextDir = File(projectPath, ".architect")
		if (!contextDir.exists() || !contextDir.isDirectory) {
			println("No .architect directory found")
			return configuration.toString()
		}

		val yamlFiles = contextDir.listFiles { file ->
			file.isFile && (file.extension.equals("yml", true) || file.extension.equals("yaml", true))
		}?.sortedBy { it.name }  // optional: deterministic order
			?: emptyList()

		if (yamlFiles.isEmpty()) {
			println("No YAML context files found in .architect")
			return configuration.toString()
		}

		yamlFiles.forEach { file ->
			try {
				println("Merging context file: ${file.name}")
				configuration.append(file.readText()).append("\n")
			} catch (e: Exception) {
				println("Failed to read file: ${file.name}, skipping. Reason: ${e.message}")
			}
		}

		return configuration.toString()
	}
}