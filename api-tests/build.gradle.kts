plugins {
    id("java")
    id("io.qameta.allure")
}

dependencies {
    testImplementation(project(":common"))
    testImplementation(files("../common/src/main/resources"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("io.rest-assured:rest-assured:5.5.0")
    testImplementation("io.qameta.allure:allure-rest-assured:2.27.0")
    testImplementation("io.qameta.allure:allure-junit5:2.27.0")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("allure.results.directory", layout.buildDirectory.dir("allure-results").get().asFile.absolutePath)
}