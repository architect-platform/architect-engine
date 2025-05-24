package io.github.architectplatform.engine.installers

data class InstallersContext(
	val owner: String,
	val name: String,
	val applicationName: String,
	val assetType: String,
)