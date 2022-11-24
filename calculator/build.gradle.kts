import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object Version {
    const val KOTEST = "5.5.4"
}

plugins {
    kotlin("jvm") version "1.6.21"
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("io.kotest:kotest-runner-junit5:${Version.KOTEST}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
