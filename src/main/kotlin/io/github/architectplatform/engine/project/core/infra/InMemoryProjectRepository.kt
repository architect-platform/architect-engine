package io.github.architectplatform.engine.project.core.infra

import io.github.architectplatform.engine.project.core.app.repositories.ProjectRepository
import io.github.architectplatform.engine.project.core.domain.Project
import io.github.architectplatform.engine.utils.InMemoryRepository
import jakarta.inject.Singleton

@Singleton
class InMemoryProjectRepository : ProjectRepository, InMemoryRepository<Project>()