package io.github.architectplatform.engine.domain.events

enum class ExecutionEventType {
  STARTED,
  TASK_STARTED,
  TASK_SKIPPED,
  TASK_COMPLETED,
  TASK_FAILED,
  COMPLETED,
  FAILED
}
