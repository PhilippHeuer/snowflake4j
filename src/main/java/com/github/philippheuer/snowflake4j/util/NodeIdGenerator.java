package com.github.philippheuer.snowflake4j.util;

import lombok.SneakyThrows;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Stream;

public class NodeIdGenerator {

    @NotNull
    public static String getNodeId() {
        // container environment
        if (isRunningInsideContainer()) {
            // use the pod / hostname of the container (k8s / docker / oci)
            if (System.getenv("HOSTNAME") != null && !"localhost".equalsIgnoreCase(System.getenv("HOSTNAME"))) {
                return hashValue(System.getenv("HOSTNAME"));
            }
        }

        // mac-address based id
        try {
            StringBuilder nodeId = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces.hasMoreElements()) {
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    nodeId.append(Arrays.hashCode(networkInterface.getHardwareAddress()));
                }

                return hashValue(nodeId.toString());
            }
        } catch (Exception ex) {
            // ignore
        }

        // fallback: random on startup
        return hashValue(String.valueOf(new SecureRandom().nextInt()));
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
        try (Stream<String> stream = Files.lines(Paths.get("/proc/1/cgroup"))) {
            return stream.anyMatch(line -> line.contains("/docker"));
        } catch (IOException e) {
            // ignore
        }

        return false;
    }

    @SneakyThrows
    @ApiStatus.Internal
    public static String hashValue(@NotNull String content) {
        return new BigInteger(1, MessageDigest.getInstance("SHA-256").digest(content.getBytes(StandardCharsets.UTF_8))).toString(16).substring(0, 16);
    }
}
