plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("org.jetbrains.kotlin.jvm") version "2.0.0-Beta1"
}

apply(plugin = "io.spring.dependency-management")

group = "com.paulrezzonico"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.21")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("com.github.cloudyrock.mongock:mongock-spring-v5:4.3.8")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0-Beta1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}
