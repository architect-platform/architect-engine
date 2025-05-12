package io.github.architectplatform.engine.core.utils

abstract class InMemoryRepository<T> : Repository<T> {
	private val objects = mutableMapOf<String, T>()

	override fun save(key: String, obj: T) {
		objects[key] = obj
	}

	override fun get(key: String): T? {
		return objects[key]
	}

	override fun getAll(): List<T> {
		return objects.values.toList()
	}
}