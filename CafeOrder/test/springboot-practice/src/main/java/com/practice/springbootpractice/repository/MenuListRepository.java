package com.practice.springbootpractice.repository;

import com.practice.springbootpractice.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuListRepository {

    private final List<Menu> menuList = new ArrayList<>();

    // 생성자에서 기본 메뉴 등록
    public MenuListRepository() {
        menuList.add(new Menu("아메리카노", 4000));
        menuList.add(new Menu("카페라떼", 4500));
        menuList.add(new Menu("바닐라라떼", 4800));
        menuList.add(new Menu("콜드브루", 5000));
    }

    public List<Menu> findAll() {
        return menuList;
    }

    // 새 메뉴 추가
    public void save(Menu menu) {
        menuList.add(menu);
    }

    // 메뉴 삭제
    public void deleteByName(String name) {
        menuList.removeIf(menu -> menu.getName().equals(name));
    }

    // 메뉴 수정 (이름으로 찾아 가격 변경)
    public void updatePrice(String name, int newPrice) {
        for (Menu menu : menuList) {
            if (menu.getName().equals(name)) {
                menu.setPrice(newPrice);
                break;
            }
        }
    }

    // 특정 메뉴 찾기
    public Menu findByName(String name) {
        for (Menu menu : menuList) {
            if (menu.getName().equals(name)) {
                return menu;
            }
        }
        return null; // 찾지 못한 경우
    }


}
