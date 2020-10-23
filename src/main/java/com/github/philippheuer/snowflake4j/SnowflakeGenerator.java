package com.github.philippheuer.snowflake4j;

import com.github.philippheuer.snowflake4j.util.NodeIdGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Optional;

/**
 * Snowflake ID Generator
 */
@ToString
public class SnowflakeGenerator {

    @Setter
    @Getter
    private static SnowflakeGenerator instance = SnowflakeGenerator.builder().epochOffset(1601510400000L).build();

    private final Long nodeId;

    private final Long epochOffset;

    private Instant lastTimestamp;

    private Long sequence;

    @Builder
    public SnowflakeGenerator(Long nodeId, Long epochOffset) {
        this.nodeId = Optional.ofNullable(nodeId).orElse(NodeIdGenerator.getNodeId());
        this.epochOffset = Optional.ofNullable(epochOffset).orElse(420070400000L);
        lastTimestamp = Instant.now().minusNanos(100);
        sequence = 0L;
    }

    public synchronized Snowflake nextSnowflake() {
        Instant timestamp = Instant.now();

        // verify that the clock wasn't running backwards
        if (timestamp.isBefore(lastTimestamp)) {
            throw new IllegalStateException("The System Clock was running backwards! [Current Timestamp:"+timestamp+"/Last Timestamp:"+lastTimestamp+"]");
        }

        if (timestamp.equals(lastTimestamp)) {
            // 2+ executions in the current millisecond / increase sequence by one
            sequence = (sequence+1) & Snowflake.NODEID_BITFIELD.getMaxValue();
            if(sequence == 0) {
                // Sequence Exhausted, wait till next millisecond.
                timestamp = waitNextMillis(timestamp);
            }
        } else {
            // next millisecond / reset sequence to zero
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return Snowflake.builder().epochOffset(epochOffset).timestamp(timestamp).nodeId(nodeId).sequenceId(sequence).build();
    }

    /**
     * Helper method in case the block for the current millisecond is exhausted
     *
     * @param currentTimestamp Current Timestamp
     * @return timestamp
     */
    private Instant waitNextMillis(Instant currentTimestamp) {
        while (currentTimestamp.equals(lastTimestamp)) {
            currentTimestamp = Instant.now();
        }

        return currentTimestamp;
    }

}
