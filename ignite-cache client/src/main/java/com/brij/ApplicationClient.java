package com.brij;

import com.brij.entities.Employee;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import javax.cache.Cache;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class ApplicationClient {
    public static void main(String[] args) {
        IgniteConfiguration cfg = new IgniteConfiguration();
        IgniteLogger slf4jLogger = new Slf4jLogger();
        cfg.setConsistentId("node2");
        cfg.setIgniteInstanceName("Ins2");
        cfg.setGridLogger(slf4jLogger);
        cfg.setClientMode(true);

        Ignite igniteClient = Ignition.start(cfg);
        System.out.printf("Existing cache %s", igniteClient.cacheNames());
        IgniteCache<Integer, String> myCache = igniteClient.getOrCreateCache("MyCache");
        myCache.put(1, new String("ss"));
        Iterator<Cache.Entry<Integer, String>> iterator = myCache.iterator();

        if (!iterator.hasNext()) {
            System.out.println("No value available");
        } else {
            iterator.forEachRemaining(d -> {
                System.out.printf("value for key %s is %s", d.getKey(), d.getValue());
            });
        }
        igniteClient.close();

    }
}
