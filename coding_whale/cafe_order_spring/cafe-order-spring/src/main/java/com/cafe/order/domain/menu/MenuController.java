package com.cafe.order.domain.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
  public String createForm(Model model) {
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



}
