package io.github.architectplatform.engine.core.project.app

import io.github.architectplatform.api.core.tasks.Environment
import io.micronaut.context.BeanContext
import io.micronaut.context.event.ApplicationEventPublisher
import jakarta.inject.Singleton

@Singleton
class ApplicationEnvironment(
    private val beanContext: BeanContext,
    private val eventPublisher: ApplicationEventPublisher<Any>
) : Environment {

  override fun <T> service(type: Class<T>): T =
      beanContext.getBean(type)
          ?: throw IllegalArgumentException("Service of type ${type.name} not found in environment")

  override fun publish(event: Any) {
    eventPublisher.publishEvent(event)
  }
}
