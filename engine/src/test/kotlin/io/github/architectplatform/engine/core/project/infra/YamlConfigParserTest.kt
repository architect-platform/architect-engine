package io.github.architectplatform.engine.core.project.infra

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class YamlConfigParserTest {
    @Test
    fun testParseValidYaml() {
        val parser = YamlConfigParser()
        val yaml = "name: TestProject\npath: /path/to/project"
        val config = parser.parse(yaml)
        assertEquals("TestProject", config.name)
        assertEquals("/path/to/project", config.path)
    }

    @Test
    fun testParseInvalidYaml() {
        val parser = YamlConfigParser()
        val yaml = "invalid: yaml"
        assertThrows<IllegalArgumentException> { parser.parse(yaml) }
    }
}

