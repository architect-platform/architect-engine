package io.github.architectplatform.engine.project.infra

import io.github.architectplatform.engine.project.app.repositories.ProjectRepository
import io.github.architectplatform.engine.project.domain.Project
import io.github.architectplatform.engine.utils.InMemoryRepository
import jakarta.inject.Singleton

@Singleton
class InMemoryProjectRepository : ProjectRepository, InMemoryRepository<Project>()