package io.github.architectplatform.engine.core.command.application.repositories

import io.github.architectplatform.api.command.Command
import io.github.architectplatform.engine.core.utils.Repository

/**
 * Registry for commands.
 * This interface allows for the registration and retrieval of commands by their name.
 * It is used to manage the available commands in the system.
 */
interface CommandRepository: Repository<Command<*>>


