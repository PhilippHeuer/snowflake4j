# *Snowflake4J*

For high availability within and across data centers, machines generating ids should not have to coordinate with each other.

**IDs are composed of:**

- time - 41 bits (millisecond precision w/ a custom epoch gives us 69 years)
- configured machine id - 10 bits - gives us up to 1024 machines
- sequence number - 12 bits - rolls over every 4096 per machine (with protection to avoid rollover in the same ms)

References:

- https://github.com/twitter/snowflake/tree/snowflake-2010


| Module                                                                                                                                                                                                | Javadoc                                                                                                                                                                                   |
|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [![Lib](https://img.shields.io/maven-central/v/com.github.philippheuer.snowflake4j/snowflake4j?label=snowflake4j)](https://search.maven.org/artifact/com.github.philippheuer.snowflake4j/snowflake4j) | [![Javadoc](https://javadoc.io/badge2/com.github.philippheuer.snowflake4j/snowflake4j/javadoc.svg?label=javadoc)](https://javadoc.io/doc/com.github.philippheuer.snowflake4j/snowflake4j) |

## Library Features

- protects from non-monotonic clocks, i.e. clocks that run backwards
- snowflake parsing (with id + epoch offset)
- id generation with a customizable epoch offset

# Import

**Gradle - Kotlin DSL**

```kotlin
implementation("com.github.philippheuer.snowflake4j:snowflake4j:1.0.1")
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

## Default Epoch

The epoch used in the default instance is `2020-10-01T00:00:00.000Z` (1601510400000L). Snowflake generation will work for ~69 years after the set epoch.

Common Epochs:

- Discord: 1420070400000

## License

Released under the [MIT License](./LICENSE).
