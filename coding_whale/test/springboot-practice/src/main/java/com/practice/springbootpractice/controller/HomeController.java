package com.practice.springbootpractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("msg", "Welcome to Spring Boot Practice!");
        return "index"; // templates/index.html로 연결
    }
}
