package com.cafe.order.domain.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/menus")
public class MenuController {

  private final MenuService menuService;


  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  @GetMapping
  public String showAllMenus(Model model) {
    model.addAttribute("menus", menuService.findAll());
    return "menu/list";
  }

  // 메뉴 생성 폼
  @GetMapping("/new")
  public String createForm() {
    return "menu/new";
  }

  // 메뉴 저장 처리
  @PostMapping("/new")
  public String create(
          @RequestParam String name,
          @RequestParam Integer price,
          @RequestParam Category category,
          @RequestParam(required = false) String description) {
    menuService.create(name, price, category, description);
    return "redirect:/menus";
  }

  // 메뉴 상세 조회
  @GetMapping("/{id}")
  public String detail(@PathVariable UUID id, Model model) {
    // Service 호출
    Menu menu = menuService.findById(id);

    // Model에 담기
    model.addAttribute("menu", menu);
    return "menu/detail";
  }


}
