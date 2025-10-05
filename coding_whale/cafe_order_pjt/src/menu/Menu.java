package menu;

// Menu 기능
public class Menu {
    private static int nextId = 1;
    private int menuId;
    private String menuName;
    private int menuPrice;
    private MenuCategory menuOption;
    private String menuRecommend;


    public Menu() {
    }

    public Menu(String menuName, int menuPrice, MenuCategory menuOption) {
        this.menuId = nextId++;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuOption = menuOption;
    }

    public Menu(int menuId, String menuName, int menuPrice, MenuCategory menuOption) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuOption = menuOption;
    }

    public Menu(String recomReason) {
        this.menuRecommend = recomReason;
    }

    public int getMenuId() {
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
