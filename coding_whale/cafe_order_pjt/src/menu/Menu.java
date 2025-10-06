package menu;

import java.util.UUID;

// Menu 기능
public class Menu {
    private UUID menuId;
    private String menuName;
    private int menuPrice;
    private MenuCategory menuOption;
    private String menuRecommend;

    // 신규 메뉴 생성 시 사용하는 생성자
    public Menu(String menuName, int menuPrice, MenuCategory menuOption) {
        this.menuId = UUID.randomUUID();
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuOption = menuOption;
    }

    // 파일에서 기존 메뉴를 로드할 때 사용할 생성자
    public Menu(UUID menuId, String menuName, int menuPrice, MenuCategory menuOption) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuOption = menuOption;
    }

    public Menu(String recomReason) {
        this.menuRecommend = recomReason;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public static void setNextId(int nextId) {
        Menu.nextId = nextId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public MenuCategory getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(MenuCategory menuOption) {
        this.menuOption = menuOption;
    }

    public String getMenuRecommend() {
        return menuRecommend;
    }

    public void setMenuRecommend(String menuRecommend) {
        this.menuRecommend = menuRecommend;
    }

    @Override
    public String toString() {
        return "[1.메뉴 이름 = " + menuName + ", 2.가격 = " + menuPrice + '원' + ", 3.옵션 = " + menuOption + ", 4.추천 여부 = " + menuRecommend + ']';
    }
}
