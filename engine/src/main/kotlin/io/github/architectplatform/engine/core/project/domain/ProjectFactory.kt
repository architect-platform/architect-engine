package io.github.architectplatform.engine.core.project.domain

import io.github.architectplatform.api.core.project.ProjectContext
import jakarta.inject.Singleton

@Singleton
class ProjectFactory {
  fun createProject(
      name: String,
      path: String,
      projectContext: ProjectContext,
      subProjects: List<Project> = emptyList()
  ): Project {
    return Project(
        name = name,
        path = path,
        context = projectContext,
        plugins = emptyList(),
        subProjects = subProjects)
  }
}
