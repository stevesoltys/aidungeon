plugins {
    kotlin("jvm") version "1.5.10"
    id("com.apollographql.apollo3").version("3.1.0")
    `maven-publish`
    java
}

group = "com.stevesoltys.aidungeon"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("com.apollographql.apollo3:apollo-runtime:3.1.0")
}

apollo {
    packageName.set("com.stevesoltys.aidungeon")
}