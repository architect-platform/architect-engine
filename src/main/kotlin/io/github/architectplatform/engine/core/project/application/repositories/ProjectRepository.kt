package io.github.architectplatform.engine.core.project.application.repositories

import io.github.architectplatform.engine.core.utils.Repository
import io.github.architectplatform.engine.core.project.application.domain.Project

/**
 * Registry for commands.
 * This interface allows for the registration and retrieval of commands by their name.
 * It is used to manage the available commands in the system.
 */
interface ProjectRepository: Repository<Project>

