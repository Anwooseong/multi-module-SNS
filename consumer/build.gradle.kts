dependencies {
    //core
    implementation(project("path" to ":core"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.kafka:spring-kafka-test")
    implementation("org.springframework.kafka:spring-kafka")

}