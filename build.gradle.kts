plugins {
    kotlin("jvm") version "1.5.10"
    id("com.apollographql.apollo3").version("3.1.0")
    `maven-publish`
}

group = "com.stevesoltys.aidungeon"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    api("com.apollographql.apollo3:apollo-runtime:3.1.0")
}

apollo {
    packageName.set("com.stevesoltys.aidungeon")
}

publishing {
    publications {
        val mavenJava by creating(MavenPublication::class) {
            from(components["kotlin"])
        }
    }
}