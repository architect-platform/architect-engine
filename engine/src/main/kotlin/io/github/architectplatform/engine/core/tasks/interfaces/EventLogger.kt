package io.github.architectplatform.engine.core.tasks.interfaces

import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.micronaut.context.annotation.Context
import io.micronaut.runtime.event.annotation.EventListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Context
class EventLogger {

  private val logger: Logger = LoggerFactory.getLogger(this::class.java)

  @EventListener
  fun onEvent(event: ArchitectEvent) {
    when (event) {
      is ExecutionEvent -> logger.info(event.toString())
      else -> {}
    }
  }
}
