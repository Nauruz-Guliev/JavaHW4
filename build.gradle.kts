plugins {
    java
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes("Main-Class" to "com.example.hwcache.HwCacheApplication")
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    //postgres
    runtimeOnly("org.postgresql:postgresql")
    //lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    // cache
    implementation("org.springframework.boot:spring-boot-starter-cache:3.1.0")
    //retrofit, moshi
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //okhttp logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    // spring web, it won't work without it even though it's not used
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    // spring aspects
    implementation("org.springframework:spring-aspects:6.0.9")
    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.0")
    implementation("io.lettuce:lettuce-core:6.2.4.RELEASE")

    implementation("org.springframework.data:spring-data-redis:3.1.0")

    implementation("com.google.code.gson:gson:2.10.1")

}

