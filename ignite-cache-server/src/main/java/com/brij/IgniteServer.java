package com.brij;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class IgniteServer {
    public static void main(String[] args) {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setGridLogger(new Slf4jLogger());
        cfg.setIgniteInstanceName("instance1");
        cfg.setConsistentId("node1");
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cfg.setCacheConfiguration(cacheConfiguration);
        Ignite igniteServer = Ignition.start(cfg);
        igniteServer.cluster().state(ClusterState.ACTIVE);
        IgniteCache<Integer, String> myCache = igniteServer.getOrCreateCache("MyCache");
        myCache.put(1, "BP");
        myCache.put(2, "BP1");
    }
}
