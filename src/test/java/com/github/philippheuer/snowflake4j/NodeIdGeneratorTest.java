package com.github.philippheuer.snowflake4j;

import com.github.philippheuer.snowflake4j.util.NodeIdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NodeIdGeneratorTest {

    @Test
    public void testNodeIdGenerator() {
        long nodeId = (long) NodeIdGenerator.getNodeId().hashCode() % Snowflake.NODEID_BITFIELD.getMaxValue();
        System.out.println("testNodeIdGenerator - NodeID: " + nodeId);

        Assertions.assertTrue(nodeId >= 0 && nodeId <= Snowflake.NODEID_BITFIELD.getMaxValue());
    }

}
