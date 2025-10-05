package menu;

public class MenuStatus {
    private int storeId;
    private int menuId;
    private MenuSaleStatus status;
    private int stock;

    public MenuStatus(int storeId, int menuId, MenuSaleStatus status, int stock) {
        this.storeId = storeId;
        this.menuId = menuId;
        this.status = status;
        this.stock = stock;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public MenuSaleStatus getStatus() {
        return status;
    }

    public void setStatus(MenuSaleStatus status) {
        this.status = status;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "MenuStatus{" +
                " storeId=" + storeId +
                ", menuId=" + menuId +
                ", status='" + status + '\'' +
                ", stock=" + stock +
                '}';
    }

}
