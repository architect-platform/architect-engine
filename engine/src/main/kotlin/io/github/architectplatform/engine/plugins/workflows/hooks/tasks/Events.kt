package io.github.architectplatform.engine.plugins.workflows.hooks.tasks

import io.github.architectplatform.engine.domain.events.ExecutionEvent
import io.github.architectplatform.engine.domain.events.ExecutionEventType
import io.micronaut.serde.annotation.Serdeable

object Events {
  @Serdeable
  data class HooksInitializationStartedEvent(
	  override val executionId: String,
	  override val success: Boolean,
	  override val message: String = "Hooks initialization started",
	  override val eventType: ExecutionEventType = ExecutionEventType.STARTED,
  ) : ExecutionEvent

  @Serdeable
  data class HooksInitializationCompletedEvent(
	  override val executionId: String,
	  override val success: Boolean,
	  override val message: String = "Hooks initialization completed",
	  override val eventType: ExecutionEventType = ExecutionEventType.COMPLETED,
  ) : ExecutionEvent

  @Serdeable
  data class HooksInitializationFailedEvent(
	  override val executionId: String,
	  override val success: Boolean = false,
	  override val message: String = "Hooks initialization failed",
	  override val eventType: ExecutionEventType = ExecutionEventType.FAILED,
  ) : ExecutionEvent

  @Serdeable
  data class HooksInitializationSkippedEvent(
	  override val executionId: String,
	  override val success: Boolean = true,
	  override val message: String = "Hooks initialization skipped",
	  override val eventType: ExecutionEventType = ExecutionEventType.SKIPPED,
  ) : ExecutionEvent

  @Serdeable
  data class HooksVerificationStartedEvent(
	  override val executionId: String,
	  override val success: Boolean,
	  override val message: String = "Hooks verification started",
	  override val eventType: ExecutionEventType = ExecutionEventType.STARTED,
  ) : ExecutionEvent

  @Serdeable
  data class HooksVerificationCompletedEvent(
	  override val executionId: String,
	  override val success: Boolean,
	  override val message: String = "Hooks verification completed",
	  override val eventType: ExecutionEventType = ExecutionEventType.COMPLETED,
  ) : ExecutionEvent

  @Serdeable
  data class HooksVerificationFailedEvent(
	  override val executionId: String,
	  override val success: Boolean = false,
	  override val message: String = "Hooks verification failed",
	  override val eventType: ExecutionEventType = ExecutionEventType.FAILED,
  ) : ExecutionEvent

  @Serdeable
  data class HooksVerificationSkippedEvent(
	  override val executionId: String,
	  override val success: Boolean = true,
	  override val message: String = "Hooks verification skipped",
	  override val eventType: ExecutionEventType = ExecutionEventType.SKIPPED,
  ) : ExecutionEvent
}