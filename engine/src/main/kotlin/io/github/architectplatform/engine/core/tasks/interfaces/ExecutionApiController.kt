package io.github.architectplatform.engine.core.tasks.interfaces

import io.github.architectplatform.engine.core.tasks.application.TaskService
import io.github.architectplatform.engine.domain.events.ArchitectEvent
import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.github.architectplatform.engine.domain.events.ExecutionEventType
import io.github.architectplatform.engine.domain.events.ExecutionId
import io.github.architectplatform.engine.domain.events.ExecutionTaskEvent
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
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
  fun getExecutionFlow(
      @PathVariable executionId: ExecutionId
  ): Flow<ArchitectEvent<ExecutionEvent>> {
    val sharedFlow = taskService.getExecutionFlow(executionId)

    return flow {
      try {
        sharedFlow.collect { eventWrapper ->
          if (eventWrapper.event !is ExecutionEvent) {
            return@collect
          }
          emit(eventWrapper)
          when (eventWrapper.event?.executionEventType) {
            ExecutionEventType.COMPLETED,
            ExecutionEventType.FAILED -> {
              if ((eventWrapper.event as? ExecutionTaskEvent)?.taskId == null) {
                error("Execution completed with event: $eventWrapper")
              }
            }
            else -> {}
          }
        }
      } catch (_: Exception) {}
    }
  }
}
