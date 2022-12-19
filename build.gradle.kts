// Remove when https://youtrack.jetbrains.com/issue/KTIJ-19369 and https://github.com/gradle/gradle/issues/22797 are fixed
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  kotlin("jvm") version libs.versions.kotlin
  alias(libs.plugins.jmh)
  alias(libs.plugins.consistent.versions)
  alias(libs.plugins.kover)
  alias(libs.plugins.sonarqube)
}

group = "name.valery1707"
version = "0.1.0-SNAPSHOT"

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.majorVersion))
  }
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(libs.jackson.databind)
  implementation(libs.mockneat)

  testImplementation(kotlin("test"))
  testImplementation(platform(libs.junit))
  testImplementation("org.junit.jupiter:junit-jupiter-params")
  testImplementation(libs.assertj)

  jmhAnnotationProcessor(libs.jmh.ann)
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

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}

jmh {
  //https://github.com/melix/jmh-gradle-plugin#configuration-options
  jmhVersion.set(libs.versions.jmh.tools)
}

sonarqube {
  properties {
    properties["sonar.projectKey"] = "valery1707_problem-solving"
    properties["sonar.organization"] = "valery1707"
    properties["sonar.host.url"] = "https://sonarcloud.io"
    properties["sonar.junit.reportPaths"] = "${project.buildDir}/test-results/test/"
    properties["sonar.coverage.jacoco.xmlReportPaths"] = "${project.buildDir}/reports/kover/xml/report.xml"
  }
}
