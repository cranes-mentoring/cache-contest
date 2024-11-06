plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.ere.contest"
version = "0.1.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.ere.contest:order-starter:0.2.2")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.hazelcast:hazelcast:5.3.5")
    implementation("com.hazelcast:hazelcast-spring:5.3.1")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")

    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
