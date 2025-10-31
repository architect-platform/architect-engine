package io.github.architectplatform.engine.core.plugin.app

data class PluginConfig(
    var name: String,
    val version: String = "latest",
    val assetType: String = "jar",
    val asset: String = "$name.$assetType",
    val type: String = "github",
    val path: String = ".",
    val owner: String = "architect-platform",
    val repo: String = "$owner/$name",
    val pattern: String = "$name-"
)
