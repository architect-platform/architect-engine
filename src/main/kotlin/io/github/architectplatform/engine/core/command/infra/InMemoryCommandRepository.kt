package io.github.architectplatform.engine.core.command.infra

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.engine.core.utils.InMemoryRepository
import io.github.architectplatform.engine.core.command.application.repositories.CommandRepository
import jakarta.inject.Singleton

@Singleton
class InMemoryCommandRepository : CommandRepository, InMemoryRepository<Command<*>>()