package com.practice.springbootpractice.repository;


import com.practice.springbootpractice.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    // 이름으로 메뉴 찾기
    Menu findByName(String name);
}

