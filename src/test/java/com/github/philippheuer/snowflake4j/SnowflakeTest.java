package com.github.philippheuer.snowflake4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class SnowflakeTest {

    @Test
    @DisplayName("Tests all Snowflake parser and render methods")
    public void testSnowflakeParser() {
        Snowflake snowflake = Snowflake.fromSnowflake(1420070400000L, 143000477531897856L);

        Assertions.assertEquals(Instant.ofEpochMilli(1454164370664L), snowflake.getTimestamp());
        Assertions.assertEquals(1420070400000L, snowflake.getEpochOffset());
        Assertions.assertEquals(0, snowflake.getNodeId());
        Assertions.assertEquals(0, snowflake.getSequenceId());
        Assertions.assertEquals(143000477531897856L, snowflake.getId());
    }

    @Test
    @DisplayName("Tests the Snowflake generator")
    public void testSnowflakeGenerator() {
        long offset = Instant.now().toEpochMilli();
        SnowflakeGenerator.setInstance(SnowflakeGenerator.builder().epochOffset(offset).nodeId(1).build());
        Snowflake snowflake = SnowflakeGenerator.getInstance().nextSnowflake();

        Assertions.assertEquals(offset, snowflake.getEpochOffset());
        Assertions.assertEquals(1, snowflake.getNodeId());
        Assertions.assertEquals(0, snowflake.getSequenceId());
    }

}
