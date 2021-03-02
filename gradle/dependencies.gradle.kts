val implementation by configurations
val testImplementation by configurations

val integrationTestImplementation by configurations.creating {
    extendsFrom(testImplementation)
}

val kotlinVersion: String by extra
val junitVersion: String by extra
val jacksonVersion: String by extra
val springDataVersion : String by extra
val springBootVersion: String by extra
val springVersion: String by extra
val dokkaVersion : String by extra

data class Package(
    val groupId: String,
    val artifactId: String,
    val version: String
)


val atomosPackages: Array<Package> = arrayOf(
    Package("com.reizu.core", "atomos-api", "0.0.1-pre-alpha"),
    Package("com.reizu.core", "atomos-util", "0.0.1-pre-alpha"),
    Package("com.reizu.core", "atomos-test", "0.0.1-pre-alpha"),
    Package("com.reizu.core", "atomos-entity", "0.0.1-pre-alpha")
)

val atomosTestPackages: Array<Package> = arrayOf(
)

val jacksonPackages: Array<Package> = arrayOf(
    Package("com.fasterxml.jackson.module", "jackson-module-kotlin", jacksonVersion),
    Package("com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml", jacksonVersion),
    Package("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310", jacksonVersion)
)

val springPackages: Array<Package> = arrayOf(
    Package("org.springframework", "spring-orm", springVersion),
    Package("org.springframework", "spring-context", springVersion),
    Package("org.springframework", "spring-context-support", springVersion)
)

val springBootPackages: Array<Package> = arrayOf(
    Package("org.springframework.boot", "spring-boot-autoconfigure", springBootVersion),
    Package("org.springframework.boot", "spring-boot", springBootVersion),
    Package("org.springframework.boot", "spring-boot-starter-actuator", springBootVersion),
    Package("org.springframework.boot", "spring-boot-starter-data-jpa", springBootVersion),
    Package("org.springframework.boot", "spring-boot-starter-parent", springBootVersion),
    Package("org.springframework.boot", "spring-boot-starter-tomcat", springBootVersion),
    Package("org.springframework.boot", "spring-boot-starter-web", springBootVersion),
    Package("org.springframework.boot", "spring-boot-gradle-plugin", springBootVersion)
)

val databasePackages: Array<Package> = arrayOf(
    Package("org.postgresql", "postgresql", "42.2.18"),
    Package("org.hibernate", "hibernate-core", "5.4.27.Final"),
    Package("org.hibernate.validator", "hibernate-validator", "7.0.0.Final")
)

val jUnitPackages: Array<Package> = arrayOf(
    Package("org.junit.jupiter", "junit-jupiter-api", junitVersion),
    Package("org.junit.jupiter", "junit-jupiter-params", junitVersion),
    Package("org.junit.jupiter", "junit-jupiter-engine", junitVersion),

    Package("io.mockk", "mockk", "1.10.5"),
    Package("org.amshove.kluent", "kluent", "1.61"),
    Package("com.nhaarman.mockitokotlin2", "mockito-kotlin", "2.2.0")
)

val springTestPackages: Array<Package> = arrayOf(
    Package("org.springframework", "spring-test", springVersion),
    Package("org.springframework.boot", "spring-boot-test", springBootVersion),
    Package("org.springframework.boot", "spring-boot-test-autoconfigure", springBootVersion)
)

val packages: Array<Package> = arrayOf(
    Package("org.jetbrains.kotlin", "kotlin-reflect", kotlinVersion),
    Package("org.jetbrains.dokka", "dokka-gradle-plugin", dokkaVersion),
    *atomosPackages,
    *jacksonPackages,
    *springPackages,
    *springBootPackages,
    *springTestPackages,
    *databasePackages,
    *jUnitPackages,
    Package("com.google.guava", "guava", "30.1-jre"),
    Package("io.swagger.core.v3", "swagger-jaxrs2", "2.1.6"),
    Package("io.swagger.core.v3", "swagger-jaxrs2-servlet-initializer", "2.1.6"),
    Package("org.springdoc", "springdoc-openapi-ui", "1.5.4"),
    Package("com.giffing.bucket4j.spring.boot.starter", "bucket4j-spring-boot-starter", "0.2.0"),
    Package("org.springframework.boot", "spring-boot-starter-cache", springBootVersion),
    Package("org.ehcache", "ehcache", "3.9.0"),
    Package("javax.cache", "cache-api", "1.1.1")
)

val testPackages: Array<Package> = arrayOf(
    Package("org.hibernate", "hibernate-testing", "5.4.27.Final"),
    *jUnitPackages,
    *springTestPackages,
    *atomosTestPackages
)

dependencies {
    packages.forEach { `package` ->
        implementation(`package`.groupId, `package`.artifactId, `package`.version)
    }

    testPackages.forEach { testPackage ->
        testImplementation(testPackage.groupId, testPackage.artifactId, testPackage.version)
    }
}
