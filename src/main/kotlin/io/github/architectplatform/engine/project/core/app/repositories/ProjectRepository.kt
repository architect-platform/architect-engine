package io.github.architectplatform.engine.project.core.app.repositories

import io.github.architectplatform.engine.project.core.domain.Project
import io.github.architectplatform.engine.utils.Repository

/**
 * Registry for commands.
 * This interface allows for the registration and retrieval of commands by their name.
 * It is used to manage the available commands in the system.
 */
interface ProjectRepository : Repository<Project>

