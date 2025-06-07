package io.github.architectplatform.engine.domain.events

interface ExecutionEvent : ArchitectEvent {
  val executionId: ExecutionId
}