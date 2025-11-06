package com.practice.springbootpractice.controller;

import com.practice.springbootpractice.model.Menu;
import com.practice.springbootpractice.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://localhost:3000") // React용 CORS 허용
@RestController
@RequestMapping("/api/menus") // 공통 경로
public class MenuApiController {

    private final MenuService menuService;

    public MenuApiController(MenuService menuService) {
        this.menuService = menuService;
    }

    // 전체 메뉴 조회
    @GetMapping
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus(); // 자동으로 JSON 변환
    }

    // 특정 메뉴 조회
    @GetMapping("/{name}")
    public Menu getMenuByName(@PathVariable String name) {
        return menuService.getMenuByName(name);
    }


    // 메뉴 추가
    @PostMapping
    public String addMenu(@RequestBody Menu newMenu) {
        menuService.addMenu(newMenu.getName(), newMenu.getPrice());
        return "메뉴가 추가되었습니다: " + newMenu.getName();
    }

    // 메뉴 수정 (PUT)
    @PutMapping("/{name}")
    public String updateMenuPrice(@PathVariable String name, @RequestBody Menu updatedMenu) {
        menuService.updateMenuPrice(name, updatedMenu.getPrice());
        return "메뉴 [" + name + "] 가격이 " + updatedMenu.getPrice() + "원으로 변경되었습니다.";
    }

    // 메뉴 삭제 (DELETE)
    @DeleteMapping("/{name}")
    public String deleteMenu(@PathVariable String name) {
        menuService.deleteMenu(name);
        return "메뉴 [" + name + "] 가 삭제되었습니다.";
    }
}
