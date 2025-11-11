package com.practice.springbootpractice.service;

import com.practice.springbootpractice.model.Menu;
import com.practice.springbootpractice.repository.MenuListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuListService {

    private final MenuListRepository menuListRepo;

    // 생성자 주입
    public MenuListService(MenuListRepository menuRepository) {
        this.menuListRepo = menuRepository;
    }

    // 모든 메뉴 가져오기
    public List<Menu> getAllMenus() {
        return menuListRepo.findAll();
    }

    // 4500 이상인 메뉴만 필터링
    public List<Menu> getExpensiveMenus() {
        return menuListRepo.findAll().stream().filter(menu -> menu.getPrice() >= 4500).collect(Collectors.toList());
    }

    // 메뉴 추가 로직
    public void addMenu(String name, int price) {
        Menu newMenu = new Menu(name, price);
        menuListRepo.save(newMenu);
    }

    // 메뉴 삭제 로직
    public void deleteMenu(String name) {
        menuListRepo.deleteByName(name);
    }

    // 메뉴 수정 로직
    public void updateMenuPrice(String name, int newPrice) {
        menuListRepo.updatePrice(name, newPrice);
    }

    // 특정 메뉴 조회
    public Menu getMenuByName(String name) {
        return menuListRepo.findByName(name);
    }

}
