package io.github.architectplatform.engine.domain.events

interface ExecutionTaskEvent : ExecutionEvent {
  val taskId: String
}
