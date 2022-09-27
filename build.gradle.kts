plugins {
  kotlin("jvm") version "1.6.21"
}

group = "name.valery1707"
version = "0.1.0-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
  testImplementation("org.assertj:assertj-core:3.22.0")
}

//Encoding
tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}
tasks.withType<Test> {
  systemProperty("file.encoding", "UTF-8")
}
tasks.withType<Javadoc> {
  options.encoding = "UTF-8"
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}
