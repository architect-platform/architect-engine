package io.github.architectplatform.engine.plugins.installers.context

data class InstallersContext(
    val enabled: Boolean = false,
    val repo: String = "architect-platform/architect",
    val name: String = "architect",
    val applicationName: String = "architect",
    val prefix: String = name,
    val assetType: String = "jar",
)
