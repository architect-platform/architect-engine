package io.github.architectplatform.engine.pipelines

import io.github.architectplatform.api.phase.SimpleTask
import io.github.architectplatform.api.plugins.ArchitectPlugin
import io.github.architectplatform.api.project.ProjectContext
import io.github.architectplatform.api.tasks.TaskRegistry
import io.github.architectplatform.api.tasks.TaskResult
import io.github.architectplatform.api.workflows.core.CoreWorkflow
import io.github.architectplatform.engine.execution.ResourceExtractor
import jakarta.inject.Singleton
import java.io.File

@Singleton
class PipelinesPlugin(
	private val resourceExtractor: ResourceExtractor,
) : ArchitectPlugin<PipelinesContext> {
	override val id: String = "pipelines-plugin"
	override val contextKey: String = ""
	override lateinit var context: PipelinesContext

	override val ctxClass: Class<PipelinesContext> = PipelinesContext::class.java

	override fun register(registry: TaskRegistry) {
		registry.add(
			SimpleTask(
				id = "init-pipelines",
				phase = CoreWorkflow.INIT,
				task = ::initPipelines,
			)
		)
	}

	private fun initPipelines(projectContext: ProjectContext): TaskResult {
		val results = this.context.pipelines.map { pipeline ->
			println("Initializing pipeline: ${pipeline.name} of type ${pipeline.type}")
			return initSinglePipeline(pipeline, projectContext)
		}
		return TaskResult.success("All pipelines initialized successfully.", results)
	}

	private fun initSinglePipeline(
		pipeline: PipelineContext,
		projectContext: ProjectContext,
	): TaskResult {
		val resourceRoot = "pipelines/"
		val resourceFile = resourceRoot + pipeline.type + ".yml"
		val pipelinesDir = File(
			projectContext.dir.toString(),
			".github/workflows"
		)

		if (!pipelinesDir.exists()) {
			pipelinesDir.mkdirs()
		}

		resourceExtractor.getResourceFileContent(resourceFile)
			.let { content ->
				val filePath = File(pipelinesDir, "${pipeline.name}.yml")
				filePath.writeText(
					content
						.replace("{{name}}", pipeline.name)
				)
			}
		return TaskResult.success("Pipeline ${pipeline.name} initialized successfully.")
	}
}

