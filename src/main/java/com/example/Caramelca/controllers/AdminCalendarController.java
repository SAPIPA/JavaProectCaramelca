package com.example.Caramelca.controllers;

import com.example.Caramelca.models.Calendar;
import com.example.Caramelca.models.Employee;
import com.example.Caramelca.repositories.CalendarRepository;
import com.example.Caramelca.repositories.EmployeeRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminCalendarController {

    private final EmployeeRepository employeeRepository;


    private final CalendarRepository calendarRepository;

    public AdminCalendarController(EmployeeRepository employeeRepository, CalendarRepository calendarRepository) {
        this.employeeRepository = employeeRepository;
        this.calendarRepository = calendarRepository;
    }

    @GetMapping("/calendar")
    public String adminCalendar(Model model) {
        Iterable<Calendar> calendars = calendarRepository.findAll();
        Iterable<Employee> employees = employeeRepository.findAll();

        /*SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for (Calendar calendar: calendars) {
            Date date = calendar.getDate();
            String formattedDate = formatter.format(date);
            try {
                calendar.setDate(formatter.parse(formattedDate));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }*/

        for (Calendar calendar: calendars) {
            Date date = calendar.getDate();
        }

        model.addAttribute("calendar", calendars);
        model.addAttribute("employees", employees);

        LocalDate minDate = LocalDate.now().plusDays(1);
        LocalDate maxDate = minDate.plusMonths(1);
        model.addAttribute("minDate", minDate);
        model.addAttribute("maxDate", maxDate);

        return "calendar";
    }

    @PostMapping("/calendar/add")
    public String calendarAdd(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                              @RequestParam @DateTimeFormat(pattern = "HH:mm") Date time,
                              @RequestParam Employee employee) {
        Calendar calendar = new Calendar(employee, date, time);
        calendarRepository.save(calendar);
        return "redirect:/calendar";
    }

    @GetMapping("/calendar/filter")
    public String calendarFilter(@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                 @RequestParam(required = false) Employee employee,
                                 Model model) {
        Iterable<Calendar> calendars = calendarRepository.findAll();
        Iterable<Employee> employees = employeeRepository.findAll();
        if(date != null) {
            calendars = calendarRepository.findByDate(date);
        }
        if(employee != null) {
            calendars = calendarRepository.findByEmployee(employee);
        }
        model.addAttribute("calendar", calendars);
        model.addAttribute("employees", employees);

        LocalDate minDate = LocalDate.now().plusDays(1);
        LocalDate maxDate = minDate.plusMonths(1);
        model.addAttribute("minDate", minDate);
        model.addAttribute("maxDate", maxDate);

        return "calendar";
    }

    @PostMapping("/calendar/delete/{id}")
    public String calendarDelete(@PathVariable(value = "id") Long id) {
        calendarRepository.deleteById(id);
        return "redirect:/calendar";
    }
}
