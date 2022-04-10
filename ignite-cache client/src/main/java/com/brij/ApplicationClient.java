package com.brij;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import java.util.Arrays;
import java.util.Collections;

public class ApplicationClient {
    public static void main(String[] args) {
        IgniteConfiguration cfg = new IgniteConfiguration();
        IgniteLogger slf4jLogger = new Slf4jLogger();
        cfg.setConsistentId("node2");
        cfg.setIgniteInstanceName("Ins2");
        cfg.setGridLogger(slf4jLogger);
        cfg.setClientMode(true);
        TcpDiscoverySpi discoSpi = new TcpDiscoverySpi();
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1:47000"));
        discoSpi.setIpFinder(ipFinder);
        cfg.setDiscoverySpi(discoSpi);
        Ignite igniteClient = Ignition.start(cfg);
        System.out.printf("Existing cache %s", igniteClient.cacheNames());
        IgniteCache<Integer, String> myCache = igniteClient.cache("MyCache");
        System.out.printf("My cache values %s", myCache.get(1));
        igniteClient.close();

    }
}
