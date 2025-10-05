package menu;

public enum MenuSaleStatus {
    AVAILABLE("판매중"),
    SOLD_OUT("품절");

    private final String displayStatus;

    MenuSaleStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }
}
