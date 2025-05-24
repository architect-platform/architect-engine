package io.github.architectplatform.engine.pipelines

data class PipelineContext(
	val name: String,
	val type: String,
)

data class PipelinesContext(
	val pipelines: List<PipelineContext>,
)