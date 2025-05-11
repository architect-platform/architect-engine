package io.github.architectplatform.engine.core.utils

abstract class InMemoryRepository<T> : Repository<T> {
	private val objects = mutableMapOf<String, T>()

	override fun register(key: String, obj: T) {
		if (objects.containsKey(key)) {
			throw IllegalArgumentException("Object with name $key already exists.")
		}
		objects[key] = obj
	}

	override fun get(key: String): T? {
		return objects[key]
	}

	override fun getAll(): List<T> {
		return objects.values.toList()
	}
}