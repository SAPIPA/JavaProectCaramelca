package com.example.Caramelca.controllers;

import com.example.Caramelca.models.*;
import com.example.Caramelca.repositories.AppointmetnRepository;
import com.example.Caramelca.repositories.CalendarRepository;
import com.example.Caramelca.repositories.Employee_ServiceRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ServiceController {

    private final CalendarRepository calendarRepository;

    private final Employee_ServiceRepository employeeServiceRepository;

    private final AppointmetnRepository appointmetnRepository;


    public ServiceController(CalendarRepository calendarRepository, Employee_ServiceRepository employeeServiceRepository, AppointmetnRepository appointmetnRepository) {
        this.calendarRepository = calendarRepository;
        this.employeeServiceRepository = employeeServiceRepository;
        this.appointmetnRepository = appointmetnRepository;
    }

    @GetMapping("/service/{id}")
    public String service(@PathVariable(value = "id") Service service,
                          Model model) {

        Iterable<Employee_Service> employee = employeeServiceRepository.findByService(service);

        Set<Employee> employees = new HashSet<>();

        for (Employee_Service employeeService : employee) {
            employees.add(employeeService.getEmployee());
        }

        Iterable<Calendar> calendars = calendarRepository.findByEmployeeIn(employees);

        model.addAttribute("calendars", calendars);
        model.addAttribute("employees", employees);


        return "service";
    }

    @GetMapping("/service/{id}/filter")
    public String serviceFilter(@PathVariable(value = "id") Service service,
                                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                @RequestParam(required = false) Employee employer,
                                Model model) {

        Iterable<Employee_Service> employee = employeeServiceRepository.findByService(service);

        Set<Employee> employees = new HashSet<>();

        for (Employee_Service employeeService : employee) {
            employees.add(employeeService.getEmployee());
        }

        model.addAttribute("employees", employees);


        Iterable<Calendar> calendars = calendarRepository.findByEmployeeIn(employees);

        if(date != null) {
            calendars = calendarRepository.findByDateAndEmployeeIn(date, employees);
        }
        if(employer != null && employees.contains(employer)) {
            employees.clear();
            employees.add(employer);
            calendars = calendarRepository.findByEmployeeIn(employees);
        }

        model.addAttribute("calendars", calendars);

        return "service";
    }

    @PostMapping("/service/{id}/appointment")
    public String appointment(@PathVariable(value = "id") Service service,
                              @RequestParam String date,
                              @RequestParam String time,
                              @RequestParam Employee employer,
                              @AuthenticationPrincipal User user) {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datE;
        Date timE;
        try {
            timE = format.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            datE = format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Appointment appointment = new Appointment(employer, service, user, datE, timE);
        appointmetnRepository.save(appointment);

        Iterable<Calendar> calendars = calendarRepository.findByDateAndEmployeeAndTime(datE, employer, timE);
        for (Calendar calendar : calendars) {
            calendarRepository.delete(calendar);
        }

        return "redirect:/index";
    }

}
