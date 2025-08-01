
dependencies {
    //core
    implementation(project("path" to ":core"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.2"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")


    implementation("org.springframework.kafka:spring-kafka")

}