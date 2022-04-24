package com.brij;

import com.brij.entities.Employee;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import javax.cache.Cache;
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

        IgniteCache<Integer, Employee> employeeCache = igniteClient.cache("EmployeeCache");
        employeeCache.put(5, new Employee(5, "emp5", "emp5@gmail.com"));
        Iterator<Cache.Entry<Integer, Employee>> iterator = employeeCache.iterator();
        if (!iterator.hasNext()) {
            System.out.println("No value found ");
        }
        iterator.forEachRemaining(d -> {
            System.out.printf("value for key %s is %s", d.getKey(), d.getValue());

        });
//        System.out.println("Employee value is " + employeeCache.get(3));
        igniteClient.close();

    }
}
