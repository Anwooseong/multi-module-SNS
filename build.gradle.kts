plugins {
	java
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.7"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

ext["springCloudVersion"] = "2023.0.0"

allprojects {
	group = "com.social"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply {
		plugin("java")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
		plugin("java-library")
	}

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(21)
		}
	}

	dependencies {
		testImplementation(platform("org.junit:junit-bom:5.10.0"))
		testImplementation("org.junit.jupiter:junit-jupiter")

		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-web")
		compileOnly("org.projectlombok:lombok")
		runtimeOnly("com.mysql:mysql-connector-j:8.2.0")
		annotationProcessor("org.projectlombok:lombok")

		implementation("org.springframework.boot:spring-boot-starter-validation")

		implementation("org.springdoc:springdoc-openapi-starter-common:2.2.0")
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

		// test
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		}
	}

	tasks.test {
		useJUnitPlatform()
	}

	tasks.compileJava{
		options.compilerArgs.add("-parameters")
	}

}