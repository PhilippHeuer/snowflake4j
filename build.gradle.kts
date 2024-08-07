plugins {
    id("java-library")
    id("me.philippheuer.configuration") version "0.11.1"
}

projectConfiguration {
    language.set(me.philippheuer.projectcfg.domain.ProjectLanguage.JAVA)
    type.set(me.philippheuer.projectcfg.domain.ProjectType.LIBRARY)
    javaVersion.set(JavaVersion.VERSION_11)

    artifactGroupId.set("com.github.philippheuer.snowflake4j")
    artifactDisplayName.set("Snowflake4J")
    artifactDescription.set("Snowflake4J")

    pom = { pom ->
        pom.url.set("https://github.com/philippheuer/snowflake4j")
        pom.issueManagement {
            system.set("GitHub")
            url.set("https://github.com/philippheuer/snowflake4j/issues")
        }
        pom.inceptionYear.set("2021")
        pom.developers {
            developer {
                id.set("PhilippHeuer")
                name.set("Philipp Heuer")
                email.set("git@philippheuer.me")
                roles.addAll("maintainer")
            }
        }
        pom.licenses {
            license {
                name.set("MIT Licence")
                distribution.set("repo")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        pom.scm {
            connection.set("scm:git:https://github.com/philippheuer/snowflake4j.git")
            developerConnection.set("scm:git:git@github.com:philippheuer/snowflake4j.git")
            url.set("https://github.com/philippheuer/snowflake4j")
        }
    }
}

dependencies {
    // annotations
    implementation("org.jetbrains:annotations:24.1.0")

    // bitfield
    api("org.apache.commons:commons-lang3:3.16.0")
}
