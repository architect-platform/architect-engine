package io.github.architectplatform.engine.core.project.infra

import io.github.architectplatform.engine.core.project.app.repositories.ProjectRepository
import io.github.architectplatform.engine.core.project.domain.Project
import io.github.architectplatform.engine.core.utils.InMemoryRepository
import jakarta.inject.Singleton

@Singleton class InMemoryProjectRepository : ProjectRepository, InMemoryRepository<Project>()
