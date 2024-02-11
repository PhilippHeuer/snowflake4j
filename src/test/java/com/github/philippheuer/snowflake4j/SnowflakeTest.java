package com.github.philippheuer.snowflake4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

class SnowflakeTest {

    @Test
    @DisplayName("Tests all Snowflake parser and render methods")
    void snowflakeParser() {
        Snowflake snowflake = Snowflake.fromSnowflake(1420070400000L, 143000477531897856L);

        assertEquals(Instant.ofEpochMilli(1454164370664L), snowflake.getTimestamp());
        assertEquals(1420070400000L, snowflake.getEpochOffset());
        assertEquals(0, snowflake.getNodeId());
        assertEquals(0, snowflake.getSequenceId());
        assertEquals(143000477531897856L, snowflake.getId());
    }

    @Test
    @DisplayName("Tests the Snowflake generator")
    void snowflakeGenerator() {
        long offset = Instant.now().toEpochMilli();
        SnowflakeGenerator.setInstance(SnowflakeGenerator.builder().epochOffset(offset).nodeId(1024).build());
        Snowflake snowflake = SnowflakeGenerator.getInstance().nextSnowflake();

        assertEquals(offset, snowflake.getEpochOffset());
        assertEquals(1024, snowflake.getNodeId());
        assertEquals(0, snowflake.getSequenceId());
    }

}
