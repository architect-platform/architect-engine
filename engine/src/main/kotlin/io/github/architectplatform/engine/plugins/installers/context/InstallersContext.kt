package io.github.architectplatform.engine.plugins.installers.context

data class InstallersContext(
    val enabled: Boolean = false,
    val owner: String = "architect-platform",
    val name: String = "",
    val applicationName: String = "",
    val assetType: String = "jar",
)
