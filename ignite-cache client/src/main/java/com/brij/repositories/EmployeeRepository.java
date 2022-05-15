package com.brij.repositories;

import com.brij.entities.Employee;
import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.springframework.stereotype.Repository;

import javax.cache.Cache;


@RepositoryConfig(cacheName = "EmployeeCache")
@Repository
public interface EmployeeRepository extends IgniteRepository<Employee, Integer> {
    Employee findById(int id);
    Cache.Entry<Integer, Employee> findByName(String name);
}
