plugins {
    java
    `maven-publish`

    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "org.ere.contest"
version = "0.1.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.3.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.2")
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.2")
    implementation("org.liquibase:liquibase-core:4.28.0")
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    runtimeOnly("org.postgresql:postgresql:42.7.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
