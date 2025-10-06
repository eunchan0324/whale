package menu;

import java.util.UUID;

public class MenuStatus {
    private int storeId;
    private UUID menuId;
    private MenuSaleStatus status;
    private int stock;

    public MenuStatus(int storeId, UUID menuId, MenuSaleStatus status, int stock) {
        this.storeId = storeId;
        this.menuId = menuId;
        this.status = status;
        this.stock = stock;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public void setMenuId(UUID menuId) {
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
