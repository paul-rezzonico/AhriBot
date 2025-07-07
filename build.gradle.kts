plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("org.sonarqube") version "5.0.0.4638"
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.5"
}

apply(plugin = "io.spring.dependency-management")

group = "com.paulrezzonico"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.6.1")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("io.mongock:mongodb-springdata-v4-driver:5.5.1")
    implementation("io.mongock:mongock-springboot:5.5.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.testcontainers:testcontainers:1.21.3")
    testImplementation("org.testcontainers:junit-jupiter:1.21.3")
    testImplementation("org.testcontainers:mongodb:1.21.3")
    testImplementation(kotlin("test"))
    "developmentOnly"("org.springframework.boot:spring-boot-devtools")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}

sonar {
    properties {
        property("sonar.projectKey", "AhriBot")
        property("sonar.projectName", "AhriBot")
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("$projectDir/config/detekt/detekt.yml"))
    baseline = file("$projectDir/config/detekt/baseline.xml")

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        reports {
            html.required.set(true)
            xml.required.set(true)
            txt.required.set(false)
            sarif.required.set(true)
        }
    }
}

