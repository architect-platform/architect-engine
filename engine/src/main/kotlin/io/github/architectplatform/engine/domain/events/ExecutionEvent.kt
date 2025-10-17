package io.github.architectplatform.engine.domain.events
//
// Execution
//

typealias ExecutionId = String

interface ExecutionEvent {
  val project: String
  val executionId: ExecutionId
  val executionEventType: ExecutionEventType
  val success: Boolean
}

enum class ExecutionEventType {
  STARTED,
  UPDATED,
  COMPLETED,
  FAILED,
  SKIPPED
}
