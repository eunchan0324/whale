package com.practice.springbootpractice.model;

public class Menu {
    private String name;
    private int price;

    public Menu(String name, int price) {
        this.name = name;
        this.price = price;
    }

    // Getter (View에서 데이터 접근할 때 필요)
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
