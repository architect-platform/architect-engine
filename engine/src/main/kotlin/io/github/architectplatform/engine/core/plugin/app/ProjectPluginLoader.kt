package io.github.architectplatform.engine.core.plugin.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.architectplatform.api.core.plugins.ArchitectPlugin
import io.github.architectplatform.api.core.project.ProjectContext
import io.github.architectplatform.api.core.project.getKey
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Singleton
import java.net.URLClassLoader

@Singleton
@ExecuteOn(TaskExecutors.BLOCKING)
class ProjectPluginLoader(
	private val spiLoader: SpiPluginLoader,
	private val downloader: PluginDownloader,
	private val internalPlugins: List<ArchitectPlugin<*>>,
	private val httpClient: HttpClient
) : PluginLoader {

	private val objectMapper = ObjectMapper().registerKotlinModule()

	override fun load(context: ProjectContext): List<ArchitectPlugin<*>> {
		val enabled = mutableListOf<ArchitectPlugin<*>>()
		// 1) Always include CorePlugin
		enabled += internalPlugins

		val rawContext = context.config.getKey<Any>("plugins") ?: Object()
		val plugins: List<PluginConfig> = when (rawContext) {
			is List<*> -> {
				// Config contains a list, so we deserialize as List<ctxClass>
				rawContext.map { item ->
					objectMapper.convertValue(item, PluginConfig::class.java)
				}
			}

			else -> {
				throw IllegalArgumentException(
					"Invalid plugins context format: expected list, got ${rawContext::class.qualifiedName}"
				)
			}
		}
		// 2) Download & load each project-declared plugin JAR
		plugins.forEach { plugin ->
			val jar = when (plugin.type) {
				"github" -> {
					val tag = if (plugin.version == "latest") {
						resolveLatestTag(plugin.owner, plugin.name)
					} else {
						"v${plugin.version}"
					}
					val url = "https://github.com/${plugin.owner}/${plugin.name}/releases/download/$tag/${plugin.asset}"
					downloader.download(url)
				}
				else -> throw IllegalArgumentException("Unsupported plugin type: ${plugin.type}")
			}
			val loader = URLClassLoader(arrayOf(jar.toURI().toURL()), this::class.java.classLoader)
			val loaded = spiLoader.loadFrom(loader)
			println("Loaded plugin ${plugin.name}: ${loaded.size} plugins found")
			enabled += loaded
		}


		return enabled
	}

	private fun resolveLatestTag(owner: String, repo: String): String {
		val url = "https://github.com/$owner/$repo/releases/latest"
		val req: HttpRequest<*> = HttpRequest.GET<Any>(url)
		val response = httpClient.toBlocking().retrieve(req, String::class.java)
			?: error("Failed to fetch latest tag from $url")
		val tagRegex = Regex("href=\"/[^/]+/$repo/releases/tag/(v[0-9.]+)\"")
		val matchResult = tagRegex.find(response)
			?: error("Failed to parse latest tag from response: $response")
		return matchResult.groupValues[1]
	}

}