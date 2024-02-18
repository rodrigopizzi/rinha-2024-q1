import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
}

group = "dev.h2r"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":modules:domain")))
    implementation("org.mongodb:mongodb-driver-sync:4.11.1")
    implementation("org.mongodb:mongodb-driver-core:4.11.1")

//    implementation("org.litote.kmongo:kmongo:4.11.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
