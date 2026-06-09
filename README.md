# Intechcore JavaFX Versions Bridge

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven-Central](https://img.shields.io/maven-central/v/com.intechcore.scomponents.fx/bridge/0.2.0-java11)](https://central.sonatype.com/artifact/com.intechcore.scomponents.fx/bridge/0.2.0-java11)
[![Maven-Central](https://img.shields.io/maven-central/v/com.intechcore.scomponents.fx/bridge/0.2.0-java8)](https://central.sonatype.com/artifact/com.intechcore.scomponents.fx/bridge/0.2.0-java8)

A compatibility bridge for working with **Java 8 JavaFX** and **Java 11+ JavaFX** across a single shared codebase.

---

## The Problem

JavaFX was bundled with the JDK up to version 8, then separated into a standalone module system in Java 11. Along the way, several internal APIs moved to different packages, some signatures changed, and a few classes were relocated or removed.

If you maintain a codebase that needs to support both environments — legacy systems on JDK 8 and modern deployments on JDK 17+ — you'd normally need to fork your code or maintain parallel branches.

## The Solution

This bridge provides **drop-in wrapper classes** that normalise the differences. You write your application once, against a single API, and the bridge selects the correct implementation at compile time based on your JDK version.

It works through simple **JDK-aware Maven profiles**:

- On **JDK 8** (`1.8`), the `bridge-java8` module is built automatically.
- On **JDK 11 or newer**, the `bridge-java11` module is built.

Both modules expose the **same public API**, so your code never knows which JDK it's targeting.

## How to Use in Your Project

### 1. Add the dependency

In your `pom.xml`, set a property for the Java major version and add the bridge:

```xml
<properties>
    <javaVersion>8</javaVersion>
    <javaVersionForLibs>-java${javaVersion}</javaVersionForLibs>
    <lib.version.tag>0.2.0</lib.version.tag>
    <lib.version>${lib.version.tag}${javaVersionForLibs}</lib.version>
    <maven.compiler.source>${javaVersion}</maven.compiler.source>
    <maven.compiler.target>${javaVersion}</maven.compiler.target>
    <java.fx.version>19</java.fx.version>
</properties>

<dependencies>
    <dependency>
        <groupId>com.intechcore.scomponents.fx</groupId>
        <artifactId>bridge</artifactId>
        <version>${lib.version}</version>
    </dependency>
</dependencies>

<profiles>
    <profile>
        <id>BuildOnJava11</id>
        <activation>
            <jdk>[11,)</jdk>
        </activation>
        <properties>
            <javaVersion>11</javaVersion>
        </properties>
        <dependencies>
            <dependency>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-controls</artifactId>
                <version>${java.fx.version}</version>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```

The bridge automatically resolves to `bridge-0.2.0-java8` or `bridge-0.2.0-java11` depending on the version you request.

### 2. Write your code against the bridge API

Import the bridge classes from `com.intechcore.scomponents.fx.bridge.*` and use them as you would the original JavaFX classes. The bridge handles the version-specific details internally.

### 3. Build with the right JDK

- For **Java 8 target**: build with `JAVA_HOME` pointing to JDK 8.
- For **Java 11+ target**: build with `JAVA_HOME` pointing to JDK 11 or newer (17, 21, etc.).

```bash
# Build the Java 11 variant
JAVA_HOME=/path/to/jdk17 mvn clean package

# Build the Java 8 variant
JAVA_HOME=/path/to/jdk8   mvn clean package
```

## Building the Project

```bash
# Full build
mvn clean package

# Build with the test desktop app
mvn clean package -PtestDesktopApp

# Deploy to Maven Central (requires GPG and Central credentials)
mvn clean deploy -PmavencentralRelease
```

## The test desktop app

The `testDesktopApp` profile builds the `test-desktop-app` module — a live JavaFX application that exercises all bridge features.

(On JDK 11+, you may need to add `--add-modules javafx.controls` JVM arguments.)

The app shows a window that demonstrates each bridge feature with live output — key codes, CSS converters, color picker skinning, named key bindings, font loading, and custom popups.

## License

This project is licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).