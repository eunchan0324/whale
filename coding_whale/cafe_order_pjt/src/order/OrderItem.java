package order;

import menu.Menu;
import menu.MenuCategory;
import menu.MenuList;

import java.util.Scanner;

public class OrderItem {
    private Menu menu;
    private int finalPrice;
    private String finalOptions;
    private String finalCup;
    private String finalTemp;

    public OrderItem(Menu menu) {
        this.menu = menu;
    }

    public OrderItem(Menu menu, int finalPrice, String finalOptions, String finalCup, String finalTemp) {
        this.menu = menu;
        this.finalPrice = finalPrice;
        this.finalOptions = finalOptions;
        this.finalCup = finalCup;
        this.finalTemp = finalTemp;
    }


    public Menu getMenu() {
        return menu;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public String getFinalOptions() {
        return finalOptions;
    }

    public String getFinalCup() {
        return finalCup;
    }

    public String getFinalTemp() {
        return finalTemp;
    }

    public void tempSelect() {
        System.out.print("온도를 선택해주세요 (HOT | ICED) : ");
        Scanner sc = new Scanner(System.in);
        String temp = sc.nextLine();

        this.finalTemp = temp;
    }

    public void cupSelect() {
        System.out.println("컵 사이즈를 입력해주세요");
        System.out.println("Tall (355ml)");
        System.out.println("Grande (473ml) + 500원");
        System.out.println("Venti (591ml) + 1000원");
        System.out.printf(" : ");

        Scanner sc = new Scanner((System.in));
        String cup = sc.nextLine();

        if (cup.equals("Grande")) {
            this.finalPrice += 500;
        } else if (cup.equals("Venti")) {
            this.finalPrice += 1000;
        }

        this.finalCup = cup;
    }

    public void optionSelect(String 주문할메뉴, MenuList menuList) {
        this.menu = menuList.findMenu(주문할메뉴);

        Scanner sc = new Scanner(System.in);

        if (menu.getMenuOption() == MenuCategory.COFFEE) {

            System.out.println("샷 옵션을 선택해주세요");
            System.out.println("1.기본(2샷) " + System.lineSeparator() + "2.연하게(1샷)" + System.lineSeparator() + "3.샷추가(3샷) + 500원" + System.lineSeparator() + "4.디카페인 + 1000원");
            System.out.print(" : ");

            int choiceOption = sc.nextInt();
            sc.nextLine();

            if (choiceOption == 1) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice();
                this.finalOptions = "기본(2샷)";
            } else if (choiceOption == 2) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice();
                this.finalOptions = "연하게(1샷)";
            } else if (choiceOption == 3) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice() + 500;
                this.finalOptions = "샷추가(3샷)";
            } else if (choiceOption == 4) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice() + 1000;
                this.finalOptions = "디카페인";
            }
        } else if (menu.getMenuOption() == MenuCategory.LATTE) {
            System.out.println("우유 옵션을 선택해주세요");
            System.out.println("1. 일반 우유");
            System.out.println("2. 오트(귀리) + 1000원");
            System.out.print(" : ");

            int choiceOption = sc.nextInt();
            sc.nextLine();

            if (choiceOption == 1) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice();
                this.finalOptions = "일반 우유";
            } else if (choiceOption == 2) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice() + 1000;
                this.finalOptions = "오트(귀리)";
            }
        } else if (menu.getMenuOption() == MenuCategory.TEA) {
            System.out.println("물 양은 선택해주세요");
            System.out.println("1. 보통");
            System.out.println("2. 적게");
            System.out.print(" : ");

            int choiceOption = sc.nextInt();
            sc.nextLine();

            if (choiceOption == 1) {
                this.finalOptions = "물 양 보통";
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice();
            } else if (choiceOption == 2) {
                this.finalOptions = "물 양 적게";
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice();

            }

        }
        System.out.println();
        System.out.println("[주문 내역 확인]");
        System.out.println("메뉴명 : " + menu.getMenuName());
        System.out.println("온도 : " + finalTemp);
        System.out.println("사이즈 : " + finalCup);
        System.out.println("옵션 : " + finalOptions);
        System.out.println("총 가격 : " + finalPrice + "원");
        System.out.println();
    }
}
