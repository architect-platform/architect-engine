package io.github.architectplatform.engine.core.utils

/**
 * Object Registry.
 */
interface Repository<T> {
	/**
	 * Registers an object with the given key and implementation.
	 *
	 * @param key The name of the object.
	 * @param obj The object implementation.
	 */
	fun save(key: String, obj: T)

	/**
	 * Retrieves an object by its key.
	 *
	 * @param key The name of the object.
	 * @return The object implementation, or null if not found.
	 */
	fun get(key: String): T?

	/**
	 * Retrieves all registered object names.
	 *
	 * @return A list of all object names.
	 */
	fun getAll(): List<T>
}

