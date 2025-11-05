package com.practice.springbootpractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class TimeController {

    @GetMapping("/time")
    public String time(Model model) {
        // 1. 현재 시간을 가져옴
        LocalDateTime now = LocalDateTime.now();

        // 2. 보기 좋은 형식으로 전환
        String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 3. view로 전달
        model.addAttribute("now", formattedTime);

        // 4. time.html로 이동
        return "time";
    }
}
