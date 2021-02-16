import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val projectGroup = "com.reizu.snaphs"
val projectArtifact = "snaphs-api"
val projectVersion = "0.0.1-pre-alpha"

group = projectGroup
version = projectVersion

apply(from = "gradle/constants.gradle.kts")

plugins {
    java
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.noarg") version "1.4.21"
    kotlin("plugin.allopen") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.sonarqube") version "3.1"
    id("org.jetbrains.dokka") version "1.4.20"
    idea
    application
    `maven-publish`
}

application {
    mainClass.set("com.reizu.snaphs.api.ApplicationKt")
}

val repoUsername: String by project
val repoToken: String by project

repositories {
    mavenCentral()
    jcenter()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/reizuseharu/Atomos-Util")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: repoUsername
            password = project.findProperty("gpr.key") as String? ?: repoToken
        }
    }
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/reizuseharu/Atomos-Test")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: repoUsername
            password = project.findProperty("gpr.key") as String? ?: repoToken
        }
    }
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/reizuseharu/Atomos-Entity")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: repoUsername
            password = project.findProperty("gpr.key") as String? ?: repoToken
        }
    }
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/reizuseharu/Atomos-API")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: repoUsername
            password = project.findProperty("gpr.key") as String? ?: repoToken
        }
    }
}


apply(from = "gradle/dependencies.gradle.kts")

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    withType<Wrapper> {
        gradleVersion = "5.0"
    }
}

sourceSets.create("integrationTest") {
    java.srcDir(file("src/integrationTest/java"))
    java.srcDir(file("src/integrationTest/kotlin"))
    resources.srcDir(file("src/integrationTest/resources"))
    compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
    runtimeClasspath += output + compileClasspath
}

val test: Test by tasks
val integrationTest by tasks.creating(Test::class) {
    description = "Runs the integration tests."
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    mustRunAfter(test)
}


val sonarHostUrl: String by project
val sonarOrganization: String by project
val sonarLogin: String by project

sonarqube {
    properties {
        property("sonar.host.url", sonarHostUrl)
        property("sonar.organization", sonarOrganization)
        property("sonar.login", sonarLogin)

        property("sonar.projectKey", "reizuseharu_SnapHS-API")
        property("sonar.projectName", "SnapHS-API")
        property("sonar.projectVersion", version)
    }
}
val sonar: Task = tasks["sonarqube"]

val check by tasks.getting {
//    dependsOn(integrationTest)
//    dependsOn(sonar)
}


val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/reizuseharu/SnapHS-API")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: repoUsername
                password = project.findProperty("gpr.key") as String? ?: repoToken
            }
        }
    }
    publications {
        register("gpr", MavenPublication::class) {
            groupId = projectGroup
            artifactId = projectArtifact
            version = projectVersion
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
