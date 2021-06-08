plugins {
    `signing`
    `java-library`
    `maven-publish`
    id("io.freefair.lombok") version "5.3.3.3"
}

group = group
version = version

// repositories
repositories {
    mavenCentral()
}

// global task customization
tasks {
    // javadoc / html5 support
    withType<Javadoc> {
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }

    // disable 'lombok.config' generation
    withType<io.freefair.gradle.plugins.lombok.tasks.GenerateLombokConfig> {
        enabled = false
    }

    // jar artifact id
    withType<Jar> {
        archiveBaseName.set(project.artifactId)
    }
}


// Lombok
lombok {
    version.set("1.18.20")
}

// Java Setup
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
    withJavadocJar()
}

// Dependencies
dependencies {
    // Logging
    api(group = "org.slf4j", name = "slf4j-api", version = "1.7.30")
    testImplementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")

    // Commons Lang
    api(group = "org.apache.commons", name = "commons-lang3", version = "3.12.0")

    // JUnit5
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.7.1")
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.7.1")
}

// subproject task customization
tasks {
    // javadoc lombok support
    val delombok by getting(io.freefair.gradle.plugins.lombok.tasks.Delombok::class)
    javadoc {
        dependsOn(delombok)
        source(delombok)
        options {
            title = "${project.artifactId} (v${project.version})"
            windowTitle = "${project.artifactId} (v${project.version})"
            encoding = "UTF-8"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "maven"
            url = uri(project.mavenRepositoryUrl)
            credentials {
                username = project.mavenRepositoryUsername
                password = project.mavenRepositoryPassword
            }
        }
    }
    publications {
        create<MavenPublication>("main") {
            from(components["java"])
            artifactId = project.artifactId
            pom.default()
        }
    }
}

publishing.publications.withType<MavenPublication> {
    pom {
        name.set("snowflake4j")
        description.set("Snowflake4J")
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["main"])
}
