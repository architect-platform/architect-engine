package io.github.architectplatform.engine.api

import io.micronaut.context.ApplicationContext
import io.micronaut.context.event.ApplicationEvent
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.runtime.Micronaut
import jakarta.inject.Singleton
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Path
import java.util.*
import java.util.concurrent.*

// ---------------------- SPI Interfaces ----------------------

// Core
enum class Phase { INIT, BUILD, TEST, DEPLOY, CLEAN }

interface Task {
	val name: String
	val dependsOn: Set<String>

	@Throws(Exception::class)
	fun execute(ctx: ProjectContext)
}

fun interface TaskFactory {
	fun create(ctx: ProjectContext): Task
}

interface Plugin {
	val id: String
	fun register(ctx: PluginContext)
}

interface PluginContext {
	val project: ProjectContext
	fun addTask(factory: TaskFactory, phase: Phase)
	fun onPhase(phase: Phase, hook: () -> Unit)
}

interface ProjectContext {
	val projectDir: Path
	fun <T : Any> service(type: Class<T>): T
}

// Documentation
interface DocumentationService {
	@Throws(Exception::class)
	fun generate(sourceDir: Path, outputDir: Path)
}

// Scripting
interface ScriptService {
	@Throws(Exception::class)
	fun runScript(scriptPath: Path, args: List<String>): List<String>
}

// HTTP Collections
interface HttpCollectionService {
	@Throws(Exception::class)
	fun runCollection(collectionFile: Path): Map<String, Int>
}

// Storage
interface StorageService {
	fun resolve(relative: String): Path

	@Throws(Exception::class)
	fun write(path: Path, data: ByteArray)

	@Throws(Exception::class)
	fun read(path: Path): ByteArray
}

// -------------------- Engine Implementation --------------------

// Application entry
object Application {
	@JvmStatic
	fun main(args: Array<String>) {
		Micronaut.build()
			.packages("com.example.engineall")
			.mainClass(Application.javaClass)
			.start()
	}
}

// HTTP Controller for CLI clients
@Controller("/api")
class EngineController(private val service: EngineService) {
	@Post("/execute")
	fun execute(@Body req: ExecuteRequest): HttpResponse<Any> {
		return try {
			val result = service.runAll(
				Path.of(req.projectPath),
				req.pluginUrls,
				req.phases
			)
			HttpResponse.ok(result)
		} catch (e: Exception) {
			HttpResponse.serverError(mapOf("error" to e.message))
		}
	}

	data class ExecuteRequest(
		val projectPath: String,
		val pluginUrls: List<URL>,
		val phases: List<Phase>,
	)
}

// Service that coordinates loading, registering, execution
@Singleton
class EngineService(
	private val registry: TaskRegistry,
	private val publisher: ApplicationEventPublisher<Any>,
) {

	fun loadPlugins(urls: List<URL>, ctx: ProjectContext) {
		urls.forEach { url ->
			URLClassLoader(arrayOf(url), this::class.java.classLoader).use { cl ->
				ServiceLoader.load(Plugin::class.java, cl).forEach { plugin ->
					plugin.register(
						EnginePluginContext(
							plugin.id, ctx, registry, publisher
						)
					)
				}
			}
		}
	}

	@Throws(Exception::class)
	fun runAll(
		projectDir: Path,
		pluginUrls: List<URL>,
		phasesToRun: List<Phase>,
	): Map<Phase, List<String>> {
		val ctx = ProjectContextImpl(projectDir)
		registry.clear()
		loadPlugins(pluginUrls, ctx)

		val results = linkedMapOf<Phase, List<String>>()
		phasesToRun.forEach { phase ->
			publisher.publishEvent(PhaseStarted(this, phase))
			val tasks = registry.tasks(phase)
			val exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
			val futures = tasks.map { task ->
				exec.submit<String> {
					task.execute(ctx)
					task.name
				}
			}
			exec.shutdown()
			exec.awaitTermination(1, TimeUnit.HOURS)
			publisher.publishEvent(PhaseCompleted(this, phase))
			results[phase] = futures.map { it.get() }
		}

		return results
	}
}

// Micronaut managed ProjectContext impl
class ProjectContextImpl(
	override val projectDir: Path,
) : ProjectContext {
	private val services = ConcurrentHashMap<Class<*>, Any>()
	override fun <T : Any> service(type: Class<T>): T =
		services.computeIfAbsent(type) {
			ApplicationContext.run().getBean(type)
		} as T
}

// Registry of tasks per phase
@Singleton
class TaskRegistry {
	private val map = mutableMapOf<Phase, MutableList<Task>>()
	fun clear() = map.clear()
	fun add(task: Task, phase: Phase) {
		map.computeIfAbsent(phase) { mutableListOf() }.add(task)
	}

	fun tasks(phase: Phase) = map[phase] ?: emptyList()
}

// PluginContext implementation
class EnginePluginContext(
	private val pluginId: String,
	private val projectCtx: ProjectContext,
	private val registry: TaskRegistry,
	private val publisher: ApplicationEventPublisher<Any>,
) : PluginContext {
	override val project: ProjectContext get() = projectCtx
	override fun addTask(factory: TaskFactory, phase: Phase) {
		val task = factory.create(projectCtx)
		registry.add(task, phase)
		publisher.publishEvent(TaskRegistered(this, task))
	}

	override fun onPhase(phase: Phase, hook: () -> Unit) {
		// TODO: Implement phase hooks
	}
}

// Events
class PhaseStarted(source: Any, val phase: Phase) : ApplicationEvent(source)
class PhaseCompleted(source: Any, val phase: Phase) : ApplicationEvent(source)
class TaskRegistered(source: Any, val task: Task) : ApplicationEvent(source)
