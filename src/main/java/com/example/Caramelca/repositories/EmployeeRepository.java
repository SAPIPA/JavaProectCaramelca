package com.example.Caramelca.repositories;

import com.example.Caramelca.models.Employee;
import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository <Employee, Long> {

    Iterable<Employee> findByName(String name);
    Iterable<Employee> findBySurname(String surname);
    Iterable<Employee> findByNumber(String number);
}
