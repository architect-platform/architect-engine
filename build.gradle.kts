plugins {
	id("org.jetbrains.kotlin.jvm") version "1.9.22"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.9.22"
	id("com.google.devtools.ksp") version "1.9.22-1.0.17"
	id("com.github.johnrengelman.shadow") version "8.1.1"
	id("io.micronaut.application") version "4.3.2"
	id("io.micronaut.aot") version "4.3.2"
}

version = "0.1"
group = "io.github.architectplatform"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
	mavenCentral()
}

dependencies {
	ksp("io.micronaut:micronaut-http-validation")
	ksp("io.micronaut.serde:micronaut-serde-processor")
	implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
	implementation("io.micronaut.serde:micronaut-serde-jackson")
	implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
	compileOnly("io.micronaut:micronaut-http-client")
	runtimeOnly("ch.qos.logback:logback-classic")
	runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
	runtimeOnly("org.yaml:snakeyaml")
	testImplementation("io.micronaut:micronaut-http-client")
}


application {
	mainClass.set("io.github.architectplatform.engine.ApplicationKt")
}

kotlin {
	jvmToolchain(17)
}

java {
	sourceCompatibility = JavaVersion.toVersion("17")
	targetCompatibility = JavaVersion.toVersion("17")
}


graalvmNative.toolchainDetection.set(false)
micronaut {
	runtime("netty")
	testRuntime("junit5")
	processing {
		incremental(true)
		annotations("io.github.architectplatform.engine.*")
	}
	aot {
		// Please review carefully the optimizations enabled below
		// Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
		optimizeServiceLoading.set(false)
		convertYamlToJava.set(false)
		precomputeOperations.set(true)
		cacheEnvironment.set(true)
		optimizeClassLoading.set(true)
		deduceEnvironment.set(true)
		optimizeNetty.set(true)
	}
}



