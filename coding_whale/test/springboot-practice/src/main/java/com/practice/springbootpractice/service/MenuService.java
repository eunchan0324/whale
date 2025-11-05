package com.practice.springbootpractice.service;

import com.practice.springbootpractice.model.Menu;
import com.practice.springbootpractice.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    // 생성자 주입
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // 모든 메뉴 가져오기
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    // 4500 이상인 메뉴만 필터링
    public List<Menu> getExpensiveMenus() {
        return menuRepository.findAll().stream().filter(menu -> menu.getPrice() >= 4500).collect(Collectors.toList());
    }

    // 메뉴 추가 로직
    public void addMenu(String name, int price) {
        Menu newMenu = new Menu(name, price);
        menuRepository.save(newMenu);
    }

    // 메뉴 삭제 로직
    public void deleteMenu(String name) {
        menuRepository.deleteByName(name);
    }

    // 메뉴 수정 로직
    public void updateMenuPrice(String name, int newPrice) {
        menuRepository.updatePrice(name, newPrice);
    }

}
