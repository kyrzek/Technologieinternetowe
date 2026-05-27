plugins {
    id("java-library")
    id("application")
    alias(libs.plugins.jetbrainsKotlinJvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies{
    implementation("io.ktor:ktor-server-core-jvm:3.4.1")
    implementation("io.ktor:ktor-server-netty-jvm:3.4.1")
    implementation("io.ktor:ktor-server-negotiation-jvm:3.4.1")
    implementation("io.ktor:ktor-specialization-kotlinx-json-jvm:3.4.1")

}

application {
    mainClass.set("pl.lipov.server.ServerKt")
}
