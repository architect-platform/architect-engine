package io.github.architectplatform.engine.project.core.app

import io.github.architectplatform.api.context.Config
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

	override fun <T : Any> service(type: Class<T>): T =
		services[type] as? T ?: throw IllegalStateException("Service not found: \$type")

	fun <T : Any> registerService(type: Class<T>, inst: T) = services.put(type, inst)
}