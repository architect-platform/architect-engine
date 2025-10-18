package io.github.architectplatform.engine.core.project.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.project.getKey
import io.github.architectplatform.engine.core.plugin.app.PluginLoader
import io.github.architectplatform.engine.core.project.app.repositories.ProjectRepository
import io.github.architectplatform.engine.core.project.domain.Project
import io.github.architectplatform.engine.core.tasks.infrastructure.InMemoryTaskRegistry
import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.io.path.Path

@Singleton
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val configLoader: ConfigLoader,
    private val pluginLoader: PluginLoader,
) {

  private val logger = LoggerFactory.getLogger(this::class.java)

  @Property(name = "architect.engine.core.project.cache.enabled", defaultValue = "true")
  var cacheEnabled: Boolean = true

  private val objectMapper =
      ObjectMapper().registerKotlinModule().apply {
        // Configure the ObjectMapper as needed
        // For example, you can enable/disable features, set visibility, etc.
        // Ignore unkown properties globally
        disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      }

  private fun loadProject(name: String, path: String): Project? {
    val projectConfig = configLoader.load(path) ?: return null
    val projectContext = ProjectContext(Path(path), projectConfig)

    // Call this method for every subfolder and build the subProjects list
    val subProjects = mutableListOf<Project>()
    val dir = File(path)
    dir.listFiles()?.forEach { file ->
      if (file.isDirectory) {
        val subProject =
            loadProject(
                file.name,
                file.absolutePath,
            )
        if (subProject != null) {
          subProjects.add(subProject)
        }
      }
    }

    println("Loading plugins for project $name at path $path")
    val plugins = pluginLoader.load(projectContext)
    val taskRegistry = InMemoryTaskRegistry()
    plugins.forEach {
      try {
        val rawContext =
            try {
              if (projectConfig.containsKey(it.contextKey) ) {
                logger.info(
                    "Project: $name, plugin ${it.id} - Context key ${it.contextKey} found in project config")
                logger.info(
                    "Project: $name, plugin ${it.id} - Using context from project config: ${projectConfig[it.contextKey]}")
                projectConfig[it.contextKey]
              } else {
                logger.info(
                    "Project: $name, plugin ${it.id} - " +
                        "Context key ${it.contextKey} not found in project config, using default context")
                it.context
              }
            } catch (_: Exception) {
              null
            }

        logger.info(
            "Project: $name, plugin ${it.id} - " + "Raw context for plugin ${it.id}: $rawContext")
        if (rawContext != null) {
          val pluginContext: Any =
              when (rawContext) {
                is List<*> -> {
                  // Config contains a list, so we deserialize as List<ctxClass>
                  rawContext.map { item -> objectMapper.convertValue(item, it.ctxClass) }
                }
                else -> {
                  // Config contains a single object (map), deserialize as ctxClass
                  objectMapper.convertValue(rawContext, it.ctxClass)
                }
              }
                  ?: throw IllegalArgumentException(
                      "Invalid context format for plugin ${it.id}: " +
                          "expected object or list, got ${rawContext::class.qualifiedName}")

          logger.info("Initializing plugin ${it.id} for project $name at path $path")
          logger.info("Plugin ${it.id} context: $pluginContext")
          it.init(pluginContext)
        }

        try {
          logger.info("Registering plugin ${it.id} for project $name - context: ${it.context}")
        } catch (_: Exception) {
          logger.info("Registering plugin ${it.id} for project $name - context: <unavailable>")
        }

        it.register(taskRegistry)
      } catch (e: Exception) {
        logger.error("Failed to initialize plugin ${it.id} for project $name at path $path")
        logger.error("Exception: ${e.message}", e)
      }
    }

    logger.info(
        "Loaded project $name at path $path with ${plugins.size} plugins and ${subProjects.size} subprojects")
    return Project(name, path, projectContext, plugins, subProjects, taskRegistry)
  }

  fun registerProject(name: String, path: String) {
    val project = projectRepository.get(name)
    if (project != null) {
      logger.debug("Project $name already registered at path ${project.path}")
      return
    }
    val newProject =
        loadProject(name, path)
            ?: throw IllegalArgumentException("Failed to load project $name from path $path")
    projectRepository.save(name, newProject)
  }

  fun getProject(name: String): Project? {
    val project = projectRepository.get(name)
    if (project != null) {
      if (!cacheEnabled) {
        println("Cache is disabled, reloading project $name")
        return loadProject(name, project.path)
      } else {
        return project
      }
    }
    return null
  }

  fun getAllProjects(): List<Project> {
    return projectRepository.getAll()
  }
}
