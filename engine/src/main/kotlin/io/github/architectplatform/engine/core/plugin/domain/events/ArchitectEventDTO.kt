package io.github.architectplatform.engine.core.plugin.domain.events

import io.github.architectplatform.engine.domain.events.AbstractArchitectEvent
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ArchitectEventDTO<Model : Any>(
    override val id: String,
    override val event: Model? = null,
) : AbstractArchitectEvent<Model>(id, event)
