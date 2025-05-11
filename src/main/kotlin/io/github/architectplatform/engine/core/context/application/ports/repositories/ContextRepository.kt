package io.github.architectplatform.engine.core.context.application.ports.repositories

import io.github.architectplatform.api.context.Context

/**
 * Registry for contexts.
 * This interface allows for the registration and retrieval of contexts by their name.
 * It is used to manage the available contexts in the system.
 */
interface ContextRepository {
	/**
	 * Registers a context with the given key and implementation.
	 *
	 * @param key The name of the context.
	 * @param obj The context implementation.
	 */
	fun register(key: String, obj: Context)

	/**
	 * Retrieves a context by its key.
	 *
	 * @param key The name of the context.
	 * @return The context implementation, or null if not found.
	 */
	fun get(key: String): Context?

	/**
	 * Retrieves all registered context names.
	 *
	 * @return A list of all context names.
	 */
	fun getAll(): List<Context>
}


