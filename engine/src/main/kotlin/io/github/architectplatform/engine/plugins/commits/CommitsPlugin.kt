package io.github.architectplatform.engine.plugins.commits

import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.api.core.tasks.TaskRegistry
import io.github.architectplatform.engine.plugins.commits.context.CommitsContext
import io.github.architectplatform.engine.plugins.commits.tasks.VerifyCommitMessageTask
import jakarta.inject.Singleton

@Singleton
class CommitsPlugin : ArchitectPlugin<CommitsContext> {
  override val id: String = "commits-plugin"
  override val contextKey: String = "commits"
  override val ctxClass: Class<CommitsContext> = CommitsContext::class.java
  override var context: CommitsContext = CommitsContext()

  override fun register(registry: TaskRegistry) {
    registry.add(VerifyCommitMessageTask(context))
  }
}
