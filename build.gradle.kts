plugins {
    kotlin("jvm") version "1.5.10"
    id("com.apollographql.apollo3").version("3.3.1")
    `maven-publish`
}

group = "com.stevesoltys.aidungeon"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {

    implementation(kotlin("stdlib"))

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    api("com.apollographql.apollo3:apollo-runtime:3.3.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
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