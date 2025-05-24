package io.github.architectplatform.engine.core.project.app

import io.github.architectplatform.api.project.Config
import io.github.architectplatform.api.project.ProjectContext
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

/**
 * Default Micronaut-based ProjectContext.
 */
class InMemoryProjectContext(
	override val dir: Path,
	override val config: Config,
	private var services: ConcurrentHashMap<Class<*>, Any>,
) : ProjectContext {

	override fun <T> service(type: Class<T>): T =
		services[type] as? T
			?: throw IllegalArgumentException("Service of type ${type.name} not found in context")

}