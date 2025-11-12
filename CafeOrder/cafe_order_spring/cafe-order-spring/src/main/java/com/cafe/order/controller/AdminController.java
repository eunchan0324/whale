package com.cafe.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // 관리자 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    // 관리자 대시보드 (메인)
    @GetMapping
    public String dashboard() {
        return "admin/dashboard";
    }

}
