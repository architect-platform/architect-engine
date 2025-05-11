package io.github.architectplatform.engine.core.project.infra

import io.github.architectplatform.engine.core.utils.InMemoryRepository
import io.github.architectplatform.engine.core.project.application.domain.Project
import io.github.architectplatform.engine.core.project.application.repositories.ProjectRepository
import jakarta.inject.Singleton

@Singleton
class InMemoryProjectRepository : ProjectRepository, InMemoryRepository<Project>()