# *Snowflake4J*

For high availability within and across data centers, machines generating ids should not have to coordinate with each other.

**IDs are composed of:**

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

```xml
<dependency>
    <groupId>com.github.philippheuer.snowflake4j</groupId>
    <artifactId>snowflake4j</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

Gradle:

```groovy
compile 'com.github.philippheuer.snowflake4j:snowflake4j:1.0.0'
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
