package io.github.architectplatform.engine.core.context.infra

import io.github.architectplatform.api.context.Context
import io.github.architectplatform.engine.core.utils.InMemoryRepository
import io.github.architectplatform.engine.core.context.application.ports.repositories.ContextRepository
import jakarta.inject.Singleton

@Singleton
class InMemoryContextRepository : ContextRepository, InMemoryRepository<Context>()