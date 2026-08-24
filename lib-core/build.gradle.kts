group = "io.github.bfur64"

plugins {
    `java-library`
    signing
    id("com.vanniktech.maven.publish") version "0.37.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit)

}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}


mavenPublishing {
    coordinates(
        group.toString(),
        "MicroSound",
        project.version.toString()
    )

    pom {
        name = "MicroSound"
        description = "A mini sound engine"
        url = "https://github.com/BFUR64/MicroSound"

        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/license/mit"
            }
        }

        developers {
            developer {
                id = "BFUR64"
                name = "Terrance"
                url = "https://github.com/BFUR64/"
            }
        }

        scm {
            url = "https://github.com/BFUR64/MicroSound"
            connection = "scm:git:https://github.com/BFUR64/MicroSound.git"
            developerConnection = "scm:git:ssh://git@github.com/BFUR64/MicroSound.git"
        }
    }

    publishToMavenCentral()
    signAllPublications()
}

signing {
    useInMemoryPgpKeys(
        providers.fileContents(
            layout.projectDirectory.file("signing-key.asc")
        ).asText.get(),
        providers.gradleProperty("signingInMemoryKeyPassword").get()
    )
}
