plugins {
  kotlin("jvm") version "1.7.10"
  id("me.champeau.jmh") version "0.6.6"
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
  testImplementation(kotlin("test"))
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.0")
  testImplementation("org.assertj:assertj-core:3.22.0")

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
