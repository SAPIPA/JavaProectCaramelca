package com.example.Caramelca.controllers;

import com.example.Caramelca.models.Calendar;
import com.example.Caramelca.models.Employee_Service;
import com.example.Caramelca.repositories.CalendarRepository;
import com.example.Caramelca.repositories.Employee_ServiceRepository;
import com.example.Caramelca.repositories.ServiceRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Controller
public class ServiceController {

    private final ServiceRepository serviceRepository;

    private final CalendarRepository calendarRepository;

    private final Employee_ServiceRepository employeeServiceRepository;


    public ServiceController(ServiceRepository serviceRepository, CalendarRepository calendarRepository, Employee_ServiceRepository employeeServiceRepository) {
        this.serviceRepository = serviceRepository;
        this.calendarRepository = calendarRepository;
        this.employeeServiceRepository = employeeServiceRepository;
    }

    @GetMapping("/service/{id}")
    public String service() {
        return "service";
    }

    @GetMapping("/service/{id}/{date}")
    public String appointment(@PathVariable(value = "id") Long id,
                              @PathVariable(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                              Model model) {

        Iterable<Employee_Service> employees = employeeServiceRepository.findByService(id);
        Iterable<Calendar> calendar = calendarRepository.findByDate(date);

        Employee_Service employeeServices;

        return "appointment";
    }
}
