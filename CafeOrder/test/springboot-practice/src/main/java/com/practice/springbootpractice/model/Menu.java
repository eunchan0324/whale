package com.practice.springbootpractice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Menu {

    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;


    // 기본 생성자 (JPA 필수)
    protected Menu() {}

    public Menu(String name, int price) {
        this.name = name;
        this.price = price;
    }



    // Getter (View에서 데이터 접근할 때 필요)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
