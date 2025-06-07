package io.github.architectplatform.engine.domain.events

import java.util.*

typealias ExecutionId = String

fun generateExecutionId(): ExecutionId {
  return UUID.randomUUID().toString().substring(0, 8)
}

interface ArchitectEvent {
  val success: Boolean
  val message: String
  val self: String
    get() = this::class.simpleName ?: "unknown_event"
}
