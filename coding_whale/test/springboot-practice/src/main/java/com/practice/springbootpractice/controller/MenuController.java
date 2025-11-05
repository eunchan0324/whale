package com.practice.springbootpractice.controller;

import com.practice.springbootpractice.model.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {

    @GetMapping("/menu")
    public String showMenu(Model model) {
        // 1. 메뉴 리스트 생성
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu("아메리카노", 4000));
        menuList.add(new Menu("카페라떼", 4500));
        menuList.add(new Menu("바닐라라떼", 4800));
        menuList.add(new Menu("콜드브루", 5000));

        // 2. View로 전달
        model.addAttribute("menus", menuList);

        // 3. template/menu.html로 전달
        return "menu";
    }
}
