package com.example.Caramelca.repositories;

import com.example.Caramelca.models.Calendar;
import com.example.Caramelca.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Long> {

    Iterable<Calendar> findByDateAndEmployeeIn(Date date, Set<Employee> employees);
    Iterable<Calendar> findByEmployee(Employee employee);
    Iterable<Calendar> findByDate(Date date);
    Iterable<Calendar> findByEmployeeIn(Set<Employee> employees);

    Iterable<Calendar> findByDateAndEmployeeAndTime(Date date, Employee employee, Date time);
}
