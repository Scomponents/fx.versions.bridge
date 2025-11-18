# Intechcore JavaFX Versions Bridge

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven-Central](https://img.shields.io/maven-central/v/com.intechcore.scomponents.fx/bridge/0.1.1-java11)](https://central.sonatype.com/artifact/com.intechcore.scomponents.fx/bridge/0.1.1-java11)
[![Maven-Central](https://img.shields.io/maven-central/v/com.intechcore.scomponents.fx/bridge/0.1.1-java8)](https://central.sonatype.com/artifact/com.intechcore.scomponents.fx/bridge/0.1.1-java8)
[![Hits-of-Code](https://hitsofcode.com/github/Scomponents/fx.versions.bridge?branch=master)](https://hitsofcode.com/github/Scomponents/fx.versions.bridge/view?branch=master)

This project provides a compatibility bridge for JavaFX, allowing you to write code that works with both Java 8 and Java 11+ versions of JavaFX.

## How it works

The project uses Maven profiles to detect the active JDK version and build the appropriate bridge module.

- If you are using JDK 1.8, the `bridge-java8` module will be built.
- If you are using JDK 11 or newer, the `bridge-java11` module will be built.

The bridge modules provide a thin layer of abstraction over the parts of the JavaFX API that have changed between Java 8 and Java 11, allowing you to write code against a single, consistent API.

## How to build

To deploy the project, run the following command from the appropriate subdirectory (bridge-java8 or bridge-java11):

```bash
mvn clean deploy -PmavencentralRelease -DjavaVersion=8
```

To build `bridge-java8`, the `mvn` should be run with JAVA_HOME=/path/to/jdk8, and for the `bridge-java11` it should be JDK 11.

## How to use

To use the bridge in your own project, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.intechcore.scomponents.fx</groupId>
    <artifactId>bridge</artifactId>
    <version>0.1.1-java${java.version.major}</version>
</dependency>
```

You will also need to set the `java.version.major` property in your `pom.xml` to either `8` or `11`, depending on the version of Java you are using. For example:

```xml
<properties>
    <java.version.major>8</java.version.major>
</properties>
```

or

```xml
<properties>
    <java.version.major>11</java.version.major>
</properties>
```
