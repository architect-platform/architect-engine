package io.github.architectplatform.engine.core.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InMemoryRepositoryTest {

  private val repository = object : InMemoryRepository<String>() {}

  @Test
  fun testSaveAndGet() {
    repository.save("key1", "value1")
    val value = repository.get("key1")
    assertThat(value).isNotNull.isEqualTo("value1")
  }

  @Test
  fun testGetAll() {
    repository.save("key1", "value1")
    repository.save("key2", "value2")
    val allValues = repository.getAll()
    assertThat(allValues).hasSize(2).containsExactlyInAnyOrder("value1", "value2")
  }

  @Test
  fun testGetNonExistentKey() {
    val value = repository.get("nonExistentKey")
    assertThat(value).isNull()
  }

  @Test
  fun testSaveOverwritesExistingValue() {
    repository.save("key1", "value1")
    repository.save("key1", "newValue1")
    val value = repository.get("key1")
    assertThat(value).isNotNull.isEqualTo("newValue1")
  }

  @Test
  fun testGetAllEmpty() {
    val allValues = repository.getAll()
    assertThat(allValues).isEmpty()
  }
}
