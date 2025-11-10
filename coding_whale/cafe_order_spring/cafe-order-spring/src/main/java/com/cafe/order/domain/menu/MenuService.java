package com.cafe.order.domain.menu;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuService {

    // JPA
//    private final JpaMenuRepository menuRepository;

    // SQL
//  private final SqlMenuRepository menuRepository;

    // InMemory
  private final InMemoryMenuRepository menuRepository;


    public MenuService(InMemoryMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // 메뉴 전체 조회
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    // 메뉴 생성
    public Menu create(String name, Integer price, Category category, String description) {
        Menu menu;

        if (description == null || description.isEmpty()) {
            menu = new Menu(name, price, category);
        } else {
            menu = new Menu(name, price, category, description);
        }

        return menuRepository.save(menu);
    }

    // 메뉴 id로 상세 조회
    public Menu findById(UUID uuid) {
//    Optional<Menu> optional = menuRepository.findById(uuid);
//    return optional.orElse(null); // 없으면 null 반환

        return menuRepository.findById(uuid).orElse(null);
    }


}
