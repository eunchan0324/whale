package com.cafe.order.domain.menu;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

  // JPA
//  private final JpaMenuRepository menuRepository;

  // SQL
  private final SqlMenuRepository menuRepository;


  public MenuService(SqlMenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  public List<Menu> findAll() {
    return menuRepository.findAll();
  }




}
