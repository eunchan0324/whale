package com.practice.springbootpractice.controller;

import com.practice.springbootpractice.model.Menu;
import com.practice.springbootpractice.service.MenuService_List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MenuController {

    private final MenuService_List menuService;

    // 생성자 주입
    public MenuController(MenuService_List menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<Menu> menuList = menuService.getAllMenus();
        model.addAttribute("menus", menuList);
        model.addAttribute("title", "전체 메뉴");
        return "menu";
    }

    @GetMapping("/menu/expensive")
    public String showExpensiveMenu(Model model) {
        List<Menu> expensiveList = menuService.getExpensiveMenus();
        model.addAttribute("menus", expensiveList);
        model.addAttribute("title", "4500원 이상 메뉴");
        return "menu";
    }

    // 메뉴 추가 화면
    @GetMapping("/menu/add")
    public String addMenuForm() {
        return "add-menu";
    }

    // 메뉴 추가 처리
    @PostMapping("/menu/add")
    public String addMenu(@RequestParam String name, @RequestParam int price) {
        menuService.addMenu(name, price);
        return "redirect:/menu";
    }

    // 메뉴 삭제 처리
    @PostMapping("/menu/delete")
    public String deleteMenu(@RequestParam String name) {
        menuService.deleteMenu(name);
        return "redirect:/menu";
    }

    // 메뉴 수정 폼
    @GetMapping("/menu/edit")
    public String editMenuForm(@RequestParam String name, Model model) {
        model.addAttribute("name", name);
        return "edit-menu";
    }


    // 메뉴 수정 처리
    @PostMapping("/menu/edit")
    public String editMenu(@RequestParam String name, @RequestParam int newPrice) {
        menuService.updateMenuPrice(name, newPrice);
        return "redirect:/menu";
    }
}
