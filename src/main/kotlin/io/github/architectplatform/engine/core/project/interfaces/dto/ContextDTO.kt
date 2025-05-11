package io.github.architectplatform.engine.core.project.interfaces.dto

import io.github.architectplatform.api.context.Context

typealias ContextDTO = Map<String, Any>

fun Context.toApiDTO(): ContextDTO {
	return this
}