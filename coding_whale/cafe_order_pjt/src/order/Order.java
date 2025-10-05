package order;

import java.time.LocalDateTime;
import java.util.ArrayList;

// Order 기능
public class Order {
    private int orderId;
    private String customerId;
    private int storeId;
    private LocalDateTime orderTime;
    private int totalPrice;
    private OrderStatus status;
    private final ArrayList<OrderItem> items = new ArrayList<>();

    public Order(String customerId, int storeId, int totalPrice, OrderStatus status, ArrayList<OrderItem> items) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.orderTime = LocalDateTime.now();
        this.totalPrice = totalPrice;
        this.status = status;
        this.items.addAll(items);
    }

    public Order(int orderId, String customerId, int storeId, LocalDateTime orderTime, int totalPrice, OrderStatus status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.storeId = storeId;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}
