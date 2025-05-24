package io.github.architectplatform.engine.components.installers

data class InstallersContext(
	val enabled: Boolean = false,
	val owner: String = "architectplatform",
	val name: String = "architect-platform",
	val applicationName: String = "architect-platform",
	val assetType: String = "jar",
)