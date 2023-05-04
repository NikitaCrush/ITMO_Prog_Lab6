plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.5.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("io.insert-koin:koin-core:3.3.3")
    implementation("io.insert-koin:koin-core:3.3.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}