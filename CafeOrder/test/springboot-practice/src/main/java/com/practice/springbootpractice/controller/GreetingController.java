package com.practice.springbootpractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting/{id}")
    public String greeting(@PathVariable int id, Model model) {
        model.addAttribute("name", id);
        return "greeting";
    }
}
