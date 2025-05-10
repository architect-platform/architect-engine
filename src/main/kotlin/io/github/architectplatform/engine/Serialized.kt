package io.github.architectplatform.engine

import io.github.architectplatform.api.interfaces.ApiCommandRequest
import io.github.architectplatform.api.interfaces.ApiCommandResponse
import io.micronaut.serde.annotation.SerdeImport

@SerdeImport(ApiCommandRequest::class)
@SerdeImport(ApiCommandResponse::class)
object Serialized