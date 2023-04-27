package com.example.Caramelca.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Хороший");
        return "greeting";
    }
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", "Хороший");
        return "index";
    }
}