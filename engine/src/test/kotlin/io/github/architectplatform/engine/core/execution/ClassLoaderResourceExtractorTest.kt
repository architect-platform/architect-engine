package io.github.architectplatform.engine.core.execution

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class ClassLoaderResourceExtractorTest {
    @Test
    fun testExtractResource() {
        val extractor = ClassLoaderResourceExtractor()
        val resourcePath = "test-resource.txt"
        val extractedPath = extractor.extract(resourcePath)
        assertNotNull(extractedPath)
        assertTrue(extractedPath.endsWith(resourcePath))
    }

    @Test
    fun testExtractNonExistentResource() {
        val extractor = ClassLoaderResourceExtractor()
        val resourcePath = "nonexistent-resource.txt"
        assertThrows<IllegalArgumentException> { extractor.extract(resourcePath) }
    }
}

