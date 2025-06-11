package io.github.architectplatform.engine.domain.events

typealias TaskId = String

interface ExecutionTaskEvent : ExecutionEvent {
  val taskId: TaskId
}
