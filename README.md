`MicroISRC` is a Java library designed to generate valid ISRCs using your purchased prefixes (Country Code + Registrant
Code) for any year.

## Table of Contents

* [Installation](https://github.com/Valkryst/MicroISRC#installation)
    * [Gradle](https://github.com/Valkryst/MicroISRC#-gradle)
    * [Maven](https://github.com/Valkryst/MicroISRC#-maven)
    * [sbt](https://github.com/Valkryst/MicroISRC#-scala-sbt)
* [ISRCRepository](https://github.com/Valkryst/MicroISRC#isrcrepository)

## Installation

MicroISRC is hosted on the [JitPack package repository](https://jitpack.io/#Valkryst/MicroISRC)
which supports Gradle, Maven, and sbt.

### ![Gradle](https://i.imgur.com/qtc6bXq.png?1) Gradle

Add JitPack to your `build.gradle` at the end of repositories.

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add MicroISRC as a dependency.

```
dependencies {
	implementation 'com.github.Valkryst:MicroISRC:1.0.0'
}
```

### ![Maven](https://i.imgur.com/2TZzobp.png?1) Maven

Add JitPack as a repository.

``` xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add MicroISRC as a dependency.

```xml
<dependency>
    <groupId>com.github.Valkryst</groupId>
    <artifactId>MicroISRC</artifactId>
    <version>1.0.0</version>
</dependency>
```

### ![Scala SBT](https://i.imgur.com/Nqv3mVd.png?1) Scala SBT

Add JitPack as a resolver.

```
resolvers += "jitpack" at "https://jitpack.io"
```

Add MicroISRC as a dependency.

```
libraryDependencies += "com.github.Valkryst" % "MicroISRC" % "1.0.0"
```

## ISRCRepository

This library has been designed in such a way as to make it agnostic of the backend storage required to enable its use.

To use `MicroISRC` you must first write a class which implements the [ISRCRepository](https://github.com/Valkryst/MicroISRC/blob/master/src/main/java/com/valkryst/MicroISRC/repository/ISRCRepository.java)
interface, then you can initialize and use the [ISRCGenerator](https://github.com/Valkryst/MicroISRC/blob/master/src/main/java/com/valkryst/MicroISRC/ISRCGenerator.java)
as follows:

```java
import com.valkryst.MicroISRC.ISRCGenerator;
import com.valkryst.MicroISRC.repository.ISRCRepository;

import java.time.Year;
import java.time.ZoneId;

public class Example {
    public static void main(final String[] args) {
        final ISRCRepository repository = null; // Initialize this using your implementation of ISRCRepository.

        final ISRCGenerator generator = ISRCGenerator.getInstance();
        generator.setRepository(repository);

        final Year currentYear = Year.of(ZoneId.systemDefault());
        System.out.println(generator.generate(currentYear));
    }
}
```

Implementations of `ISRCRepository` must adhere to the documentation of the interface and its methods, with special
care required to ensure the methods are atomic and thread-safe. An example can be found [here](https://github.com/Valkryst/MicroISRC/blob/master/src/test/java/com/valkryst/MicroISRC/repository/InMemoryISRCRepository.java).