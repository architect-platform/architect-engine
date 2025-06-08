package io.github.architectplatform.engine.core.tasks.interfaces

import io.github.architectplatform.engine.core.tasks.application.TaskService
import io.github.architectplatform.engine.core.tasks.domain.events.ExecutionCompletedEvent
import io.github.architectplatform.engine.core.tasks.domain.events.ExecutionFailedEvent
import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.github.architectplatform.engine.domain.events.ExecutionId
import io.micronaut.context.annotation.Context
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/api/executions")
@ExecuteOn(TaskExecutors.IO)
class ExecutionApiController(private val taskService: TaskService) {

  private val logger: Logger = LoggerFactory.getLogger(this::class.java)

  @Get("/{executionId}")
  fun getExecutionFlow(@PathVariable executionId: ExecutionId): Flow<ExecutionEvent> {
    println("Getting execution flow for ID: $executionId")
    val sharedFlow = taskService.getExecutionFlow(executionId)

    return flow {
      try {
        sharedFlow.collect { event ->
          emit(event)
          when (event) {
            is ExecutionCompletedEvent,
            is ExecutionFailedEvent -> {
              error("Execution completed with event: $event")
            }
            else -> logger.info("Emitting event: $event")
          }
        }
      } catch (_: Exception) {}
    }
  }
}

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
