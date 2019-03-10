plugins {
    id("org.springframework.boot") version "2.1.3.RELEASE"
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
    }

    version = "1.0"

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
