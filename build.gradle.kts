plugins {
    kotlin("jvm") version "2.3.20"
    kotlin("plugin.serialization") version "2.3.20"
    application
}

group = "ru.mirea.shylit"
version = "1.0.0"

repositories {
    mavenCentral()
}

val ktorVersion = "3.4.0"
val logbackVersion = "1.5.18"

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation(kotlin("test"))

    implementation("io.ktor:ktor-server-content-negotiation:${ktorVersion}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${ktorVersion}")
}

application {
    mainClass.set("ru.mirea.shylit.studydeadline.ApplicationKt")
}

tasks.test {
    useJUnitPlatform()
}