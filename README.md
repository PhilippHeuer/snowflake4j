# *Snowflake4J*

For high availability within and across data centers, machines generating ids should not have to coordinate with each other.

**ID's are composed of:**
- time - 41 bits (millisecond precision w/ a custom epoch gives us 69 years)
- configured machine id - 10 bits - gives us up to 1024 machines
- sequence number - 12 bits - rolls over every 4096 per machine (with protection to avoid rollover in the same ms)

References:

- https://github.com/twitter/snowflake/tree/snowflake-2010

## Library Features

- protects from non-monotonic clocks, i.e. clocks that run backwards
- snowflake parsing (with id + epoch offset)
- id generation with a customizable epoch offset

# Import

Maven:

Add the repository to your pom.xml with:
```xml
<repositories>
    <repository>
      <id>jcenter</id>
      <url>https://jcenter.bintray.com/</url>
    </repository>
</repositories>
```
and the dependency: (latest, you should use the actual version here)

```xml
<dependency>
    <groupId>com.github.philippheuer.snowflake4j</groupId>
    <artifactId>snowflake4j</artifactId>
    <version>0.1.1</version>
    <type>pom</type>
</dependency>
```

Gradle:

Add the repository to your build.gradle with:
```groovy
repositories {
	jcenter()
}
```

and the dependency:

```groovy
compile 'com.github.philippheuer.snowflake4j:snowflake4j:0.1.1'
```

# Usage

## Id Generation

```java
// initialize global generator instance
SnowflakeGenerator.setInstance(SnowflakeGenerator.builder().epochOffset(offset).nodeId(1).build());
// get id
Snowflake snowflake = SnowflakeGenerator.getInstance().nextSnowflake();
System.out.println(snowflake.getId());
```

## Parsing

```java
Snowflake snowflake = Snowflake.fromSnowflake(1420070400000L, 143000477531897856L);
```

## License

Released under the [MIT License](./LICENSE).
