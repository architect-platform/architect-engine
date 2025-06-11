package io.github.architectplatform.engine.domain.events

import java.util.*

fun generateExecutionId(): ExecutionId {
  return UUID.randomUUID().toString().substring(0, 8)
}

typealias ArchitectEventId = String

interface ArchitectEvent<Model : Any> {
  val id: ArchitectEventId
  val event: Model?
}

abstract class AbstractArchitectEvent<Model : Any>(
    override val id: ArchitectEventId,
    override val event: Model? = null,
) : ArchitectEvent<Model>
