plugins {
  kotlin("jvm") version "1.7.20"
  id("me.champeau.jmh") version "0.6.8"
  id("com.palantir.consistent-versions") version "2.11.0"
  id("org.jetbrains.kotlinx.kover") version "0.6.1"
  id("org.sonarqube") version "3.4.0.2513"
}

group = "name.valery1707"
version = "0.1.0-SNAPSHOT"

val jmhVersionCust = "1.35"

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
  implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4.2")

  testImplementation(kotlin("test"))
  testImplementation(platform("org.junit:junit-bom"))
  testImplementation("org.junit.jupiter:junit-jupiter-params")
  testImplementation("org.assertj:assertj-core")

  jmhAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:$jmhVersionCust")
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

jmh {
  //https://github.com/melix/jmh-gradle-plugin#configuration-options
  jmhVersion.set(jmhVersionCust)
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
