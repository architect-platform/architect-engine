package io.github.architectplatform.engine.core.project.interfaces.dto

import io.github.architectplatform.api.context.Context

typealias ContextDTO = Context

fun Context.toDTO(): ContextDTO {
	return this
}