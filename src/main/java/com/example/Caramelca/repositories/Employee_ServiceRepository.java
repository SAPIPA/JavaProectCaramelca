package com.example.Caramelca.repositories;

import com.example.Caramelca.models.Employee_Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Employee_ServiceRepository extends CrudRepository<Employee_Service, Long> {
    Iterable<Employee_Service> findByService(Long id);
}
