package com.cafe.order.domain.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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






}
