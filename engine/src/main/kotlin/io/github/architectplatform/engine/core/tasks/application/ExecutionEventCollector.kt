package io.github.architectplatform.engine.core.tasks.application

import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.github.architectplatform.engine.domain.events.ExecutionId
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.slf4j.LoggerFactory

@Singleton
class ExecutionEventCollector {

  private val logger = LoggerFactory.getLogger(this::class.java)
  private val flows = mutableMapOf<ExecutionId, MutableSharedFlow<ArchitectEvent<ExecutionEvent>>>()

  private fun newFlow(): MutableSharedFlow<ArchitectEvent<ExecutionEvent>> =
      MutableSharedFlow(
          replay = 64, // replay last 64 events to new subscribers
          extraBufferCapacity = 64, // allow buffering more before suspending emitters
          onBufferOverflow = BufferOverflow.DROP_OLDEST)

  fun getFlow(executionId: ExecutionId): Flow<ArchitectEvent<ExecutionEvent>> =
      synchronized(flows) {
        // Subscribers share this flow
        flows.getOrPut(executionId) { newFlow() }
      }

  @EventListener
  fun onExecutionEvent(eventWrapper: ArchitectEvent<*>) {
    val event = eventWrapper.event
    if (event is ExecutionEvent) {
      val flow = flows.getOrPut(event.executionId) { newFlow() }
      val emitted = flow.tryEmit(eventWrapper as ArchitectEvent<ExecutionEvent>)
      if (!emitted) {
        logger.warn("Could not emit event for ${event.executionId}")
      }
    }
  }
}
