package io.github.architectplatform.engine.project.core.interfaces.dto

import io.github.architectplatform.api.context.Config

typealias ConfigDTO = Config

fun Config.toDTO(): ConfigDTO {
	return this
}