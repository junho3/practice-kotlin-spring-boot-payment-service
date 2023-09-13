import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

allprojects {
    repositories {
        mavenCentral()
        maven(url = "https://repo.spring.io/release")
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    group = "com.example"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.kotest:kotest-runner-junit5:4.4.3")
        testImplementation("io.kotest:kotest-assertions-core:4.4.3")
        testImplementation("io.kotest:kotest-extensions-spring-jvm:4.4.3")
        testImplementation("io.mockk:mockk:1.9.3")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":common") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = false
    jar.enabled = true
}

project(":domain") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation(project(":common"))
        implementation("javax.transaction:javax.transaction-api")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    }
}

project(":payment-api") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = true
    jar.enabled = false

    springBoot {
        buildInfo()
    }

    dependencies {
        implementation(project(":common"))
        implementation(project(":domain"))
        implementation(project(":persistence"))
        implementation(project(":clients"))
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework:spring-context")
    }
}

project(":clients") {
    // FeignClient 로 API 통신을 DB 인서트로 대체
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation(project(":common"))
        implementation(project(":domain"))
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        runtimeOnly("com.h2database:h2")
    }
}

project(":persistence") {
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation(project(":common"))
        implementation(project(":domain"))
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        runtimeOnly("com.h2database:h2")
    }
}
