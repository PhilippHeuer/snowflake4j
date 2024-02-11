package com.github.philippheuer.snowflake4j;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.philippheuer.snowflake4j.util.NodeIdGenerator;
import org.junit.jupiter.api.Test;

class NodeIdGeneratorTest {

    @Test
    void nodeIdGenerator() {
        String nodeId = NodeIdGenerator.getNodeId();
        assertNotNull(nodeId);
    }

}
