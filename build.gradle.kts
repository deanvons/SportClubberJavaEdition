plugins {
	java
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "no.loopacademy"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	// https://mvnrepository.com/artifact/org.projectlombok/lombok
	implementation("org.projectlombok:lombok:1.18.38")
	// NB!!!!!
	annotationProcessor("org.projectlombok:lombok:1.18.38")

	// Mapstruct
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

	// Validation annotations (e.g., @NotBlank, @Size)
	implementation("jakarta.validation:jakarta.validation-api:3.0.2")

	// Hibernate Validator (enables validation at runtime)
	implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")

	// Optional: If you need validation of @Valid in your controllers
	implementation("org.springframework.boot:spring-boot-starter-validation")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
