package io.github.architectplatform.engine.core.project.app

import io.github.architectplatform.api.core.project.Config
import io.github.architectplatform.engine.core.project.app.ports.ConfigParser
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.io.File

@Singleton
class ConfigLoader(private val configParser: ConfigParser) {

  private val logger = LoggerFactory.getLogger(ConfigLoader::class.java)

  fun load(path: String): Config? {
    val yamlContext = getExternalConfiguration(path)
    if (yamlContext.isEmpty()) {
      return null
    }
    return configParser.parse(yamlContext)
  }

  private fun getExternalConfiguration(projectPath: String = "."): String {
    val configuration = StringBuilder()

    // 1) Load the main architect.yml/yaml if present
    val rootYaml =
        File(projectPath, "architect.yml").takeIf { it.exists() }
            ?: File(projectPath, "architect.yaml").takeIf { it.exists() }

    if (rootYaml == null || rootYaml.readText().isEmpty()) {
      return configuration.toString()
    }

    try {
      configuration.append(rootYaml.readText()).append("\n")
    } catch (e: Exception) {
      logger.error("Failed to read ${rootYaml.name}, skipping.", e)
    }

    // 2) Load all files in .architect folder
    val contextDir = File(projectPath, ".architect")
    if (!contextDir.exists() || !contextDir.isDirectory) {
      return configuration.toString()
    }

    val yamlFiles =
        contextDir
            .listFiles { file ->
              file.isFile &&
                  (file.extension.equals("yml", true) || file.extension.equals("yaml", true))
            }
            ?.sortedBy { it.name } // optional: deterministic order
        ?: emptyList()

    if (yamlFiles.isEmpty()) {
      return configuration.toString()
    }

    yamlFiles.forEach { file ->
      try {
        configuration.append(file.readText()).append("\n")
      } catch (e: Exception) {
        logger.error("Failed to read ${file.name}, skipping.", e)
      }
    }

    return configuration.toString()
  }
}
