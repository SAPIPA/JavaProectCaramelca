package com.example.Caramelca.controllers;

import com.example.Caramelca.models.Calendar;
import com.example.Caramelca.models.Employee;
import com.example.Caramelca.repositories.AdminCalendarRepository;
import com.example.Caramelca.repositories.EmployeeRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminCalendarController {

    private final EmployeeRepository employeeRepository;


    private final AdminCalendarRepository adminCalendarRepository;

    public AdminCalendarController(EmployeeRepository employeeRepository, AdminCalendarRepository adminCalendarRepository) {
        this.employeeRepository = employeeRepository;
        this.adminCalendarRepository = adminCalendarRepository;
    }

    @GetMapping("/calendar")
    public String adminCalendar(Model model) {
        Iterable<Calendar> calendars = adminCalendarRepository.findAll();
        Iterable<Employee> employees = employeeRepository.findAll();

        model.addAttribute("calendar", calendars);
        model.addAttribute("employees", employees);

        return "calendar";
    }

    @PostMapping("/calendar/add")
    public String calendarAdd(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                              @RequestParam @DateTimeFormat(pattern = "HH:mm") Date time,
                              @RequestParam Employee employee,
                              Model model) {

        Iterable<Employee> employees = employeeRepository.findAll();

        model.addAttribute("employees", employees);

        Calendar calendar = new Calendar(employee, date, time);
        adminCalendarRepository.save(calendar);
        return "redirect:/calendar";
    }



    /*@GetMapping("/calendar")
    public String showCalendar(Model model) {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today;
        LocalDate endOfMonth = startOfMonth.plusMonths(1).withDayOfMonth(today.getDayOfMonth());

        List<LocalDate> days = new ArrayList<>();
        LocalDate date = startOfMonth;
        while (!date.isAfter(endOfMonth)) {
            days.add(date);
            date = date.plusDays(1);
        }

        model.addAttribute("days", days);

        return "calendar";
    } */
}
