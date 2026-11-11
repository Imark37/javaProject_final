plugins {
    id("java")
    id("io.qameta.allure") version "3.0.0" apply false
}

allprojects {
    group = "org.example"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        systemProperty("file.encoding", "UTF-8")
    }
    // Smoke-прогон API-тестов
    tasks.register("apiSmoke") {
        dependsOn(":api-tests:test")
    }

    // Smoke-прогон UI-тестов
    tasks.register("uiSmoke") {
        dependsOn(":ui-tests:test")
    }
}
