val queryDslVersion = "5.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.testcontainers:testcontainers:1.20.1")
    testImplementation("org.testcontainers:mongodb:1.20.1")

    // QueryDSL Implementation
    implementation ("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
}

/**
 * QueryDSL Build Options
 */
val querydslDir = "src/main/generated"

sourceSets {
    getByName("main").java.srcDirs(querydslDir)
}

tasks.withType<JavaCompile> {
//    options.generatedSourceOutputDirectory = file(querydslDir)

    // 위의 설정이 안되면 아래 설정 사용
     options.generatedSourceOutputDirectory.set(file(querydslDir))
}

tasks.named("clean") {
    doLast {
        file(querydslDir).deleteRecursively()
    }
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }
}
