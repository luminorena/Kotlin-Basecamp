plugins {
    kotlin("jvm") version "2.2.20"
    application
}

group = "ru.basecamp"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("io.mockk:mockk:1.13.10")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("ru.basecamp.Main.kt")
}

kotlin {
    jvmToolchain(17)
}