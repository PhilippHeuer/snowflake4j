package com.github.philippheuer.snowflake4j.util;

import com.github.philippheuer.snowflake4j.Snowflake;

import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Stream;

public class NodeIdGenerator {

    public static String getNodeId() {
        // container environment
        if (isRunningInsideContainer()) {
            // use the pod / hostname of the container (k8s / docker / oci)
            if (System.getenv("HOSTNAME") != null && !System.getenv("HOSTNAME").equalsIgnoreCase("localhost")) {
                return System.getenv("HOSTNAME");
            }
        }

        // mac-address based id
        try {
            StringBuilder nodeId = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces.hasMoreElements()) {
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    byte[] mac = networkInterface.getHardwareAddress();
                    nodeId.append(Arrays.hashCode(mac));
                }

                return nodeId.toString();
            }
        } catch (Exception ex) {
            // ignore
        }

        // fallback: random on startup
        return Integer.valueOf(new SecureRandom().nextInt()).toString();
    }

    /**
     * Checks if this process is running in a container
     *
     * @return true if it's running in a container
     */
    public static Boolean isRunningInsideContainer() {
        // oci container runtime
        if (!System.getProperty("os.name", "generic").contains("win") && new File("/run/.containerenv").exists()) {
            return true;
        }

        // docker container
        try (Stream< String > stream = Files.lines(Paths.get("/proc/1/cgroup"))) {
            return stream.anyMatch(line -> line.contains("/docker"));
        } catch (IOException e) {
            // ignore
        }

        return false;
    }
}
