package io.github.architectplatform.engine.core.project.app

import io.github.architectplatform.api.components.execution.CommandExecutor
import io.github.architectplatform.api.components.execution.ResourceExtractor
import io.github.architectplatform.api.core.tasks.Environment
import jakarta.inject.Singleton
import java.util.concurrent.ConcurrentHashMap

@Singleton
class ApplicationEnvironment(
    commandExecutor: CommandExecutor,
    resourceExtractor: ResourceExtractor
) : Environment {
  private val services: ConcurrentHashMap<Class<*>, Any> =
      ConcurrentHashMap(
          mapOf(
              CommandExecutor::class.java to commandExecutor,
              ResourceExtractor::class.java to resourceExtractor))

  override fun <T> service(type: Class<T>): T =
      services[type] as? T
          ?: throw IllegalArgumentException("Service of type ${type.name} not found in environment")
}
