package order;

import constant.Constants;
import menu.Menu;
import menu.MenuList;
import store.StoreList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderList {
    private ArrayList<Order> orderList = new ArrayList<>();
    private int nextOrderId = 1;
    private MenuList menuList;
    private StoreList storeList;

    public OrderList(MenuList menuList, StoreList storeList) throws IOException {
        this.menuList = menuList;
        this.storeList = storeList;
        loadOrderFile();
    }

    public void setNextOrderId(int nextOrderId) {
        this.nextOrderId = nextOrderId;
    }

    public void addOrder(Order order) throws IOException {
        order.setOrderId(nextOrderId);
        this.orderList.add(order);
        nextOrderId++;
        saveOrderFile();

    }

    public void saveOrderFile() throws IOException {
        Path orderFilePath = Constants.BASE_PATH.resolve("Orders.txt");
        FileWriter orderWriter = new FileWriter(orderFilePath.toFile());

        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            orderWriter.write(order.getOrderId() + "," +
                    order.getCustomerId() + "," +
                    order.getStoreId() + "," +
                    order.getOrderTime() + "," +
                    order.getTotalPrice() + "," +
                    order.getStatus() + "\n");
        }
        orderWriter.close();


        Path orderItemsFilePath = Constants.BASE_PATH.resolve("Order_items.txt");
        FileWriter orderItemsWriter = new FileWriter(orderItemsFilePath.toFile());

        for (Order order : orderList) {
            for (OrderItem item : order.getItems()) {
                orderItemsWriter.write(order.getOrderId() + "," +
                        item.getMenu().getMenuId() + "," +
                        item.getFinalPrice() + "," +
                        item.getFinalTemp() + "," +
                        item.getFinalCup() + "," +
                        item.getFinalOptions() + "\n");
            }
        }
        orderItemsWriter.close();
    }

    public void loadOrderFile() throws IOException {
        Path orderFilePath = Constants.BASE_PATH.resolve("Orders.txt");
        BufferedReader orderReader = new BufferedReader((new FileReader(orderFilePath.toFile())));

        // 임시 보관소
        Map<Integer, Order> ordersMap = new HashMap<>();
        String line;
        int maxId = 0;

        while ((line = orderReader.readLine()) != null) {
            String[] parts = line.split(",");
            int orderId = Integer.parseInt(parts[0]);
            String customerId = parts[1];
            int storeId = Integer.parseInt(parts[2]);
            LocalDateTime orderTime = LocalDateTime.parse(parts[3]);
            int totalPrice = Integer.parseInt(parts[4]);
            OrderStatus status = OrderStatus.valueOf(parts[5]);

            // 임시 객체 생성자
            Order order = new Order(orderId, customerId, storeId, orderTime, totalPrice, status);
            ordersMap.put(orderId, order);

            maxId = Math.max(maxId, orderId);
        }
        orderReader.close();
        setNextOrderId(maxId + 1);

        Path orderItemsFilePath = Constants.BASE_PATH.resolve("Order_items.txt");
        BufferedReader orderItemsReader = new BufferedReader((new FileReader(orderItemsFilePath.toFile())));


        while ((line = orderItemsReader.readLine()) != null) {
            String[] parts = line.split((","));
            int orderId = Integer.parseInt(parts[0]);
            int menuId = Integer.parseInt(parts[1]);
            int price = Integer.parseInt(parts[2]);
            String temp = parts[3];
            String cup = parts[4];
            String options = parts[5];

            // 1. orderId로 Map 임시 보관소에서 targetOrder 찾기
            Order targetOrder = ordersMap.get(orderId);

            // 안전장치
            if (targetOrder == null) {
                // 해당하는 주문이 없으면 건너뛰기
                continue;
            }

            //2. order.OrderItem 생성
            Menu menu = menuList.getMenuById(menuId); // menuId로 menu 객체 찾기
            OrderItem item = new OrderItem(menu, price, temp, cup, options);

            // 3. Order에 order.OrderItem 추가
            targetOrder.getItems().add(item);

        }
        orderItemsReader.close();

        // 모든 로딩이 끝난 후, Map의 모든 order.Order 객체를 실제 orderList에 추가
        orderList.addAll(ordersMap.values());
    }

    public void checkOrders() {
        if (orderList.isEmpty()) {
            System.out.println("현재 주문 내역이 존재하지 않습니다.");
            return;
        }


        System.out.println("--- 전체 주문 내역 --- ");
        // 1. 바깥쪽 루프 : 전체 주문(order.Order) 목록 순회
        for (Order order : orderList) {
            System.out.println("===================================");
            System.out.println("주문 번호 : " + order.getOrderId());
            System.out.println("주문 시간 : " + order.getOrderTime());
            System.out.println("주문 상태 : " + order.getStatus());
            System.out.println("--- 주문 메뉴 목록 ---");

            // 2. 안쪽 루프 : 해당 주문(order.Order)에 포함된 메뉴(order.OrderItem) 목록 순회
            for (OrderItem item : order.getItems()) {
                System.out.println("- 메뉴 : " + item.getMenu().getMenuName() +
                        " | 온도 : " + item.getFinalTemp() +
                        " | 컵 : " + item.getFinalCup() +
                        " | 옵션 : " + item.getFinalOptions());
            }

            System.out.println("-----------------------------------");
            System.out.println("총 결제 금액 : " + order.getTotalPrice() + "원");
            System.out.println("===================================");
            System.out.println();


        }

    }

    public void checkOrders(String customerId) {
        System.out.println();
        for (Order order : orderList) {
            if (order.getCustomerId().equals(customerId)) {
                System.out.println("===================================");
                System.out.println("주문 번호 : " + order.getOrderId());
                System.out.println("주문 시간 : " + order.getOrderTime());
                System.out.println("주문 상태 : " + order.getStatus());
                System.out.println("--- 주문 메뉴 목록 ---");

                for (OrderItem item : order.getItems()) {
                    System.out.println("- 메뉴 : " + item.getMenu().getMenuName() +
                            " | 온도 : " + item.getFinalTemp() +
                            " | 컵 : " + item.getFinalCup() +
                            " | 옵션 : " + item.getFinalOptions());
                }

                System.out.println("-----------------------------------");
                System.out.println("총 결제 금액 : " + order.getTotalPrice() + "원");
                System.out.println("===================================");
                System.out.println();

            }
        }
    }

    public void showPendingOrders() {

        if (orderList.isEmpty()) {
            System.out.println("현재 주문 내역이 존재하지 않습니다.");
            return;
        }

        System.out.println();
        System.out.println("--- 주문 상태 변경 가능 내역 ---");
        for (Order order : orderList) {
            // 주문 완료가 아닌 것들만
            if (order.getStatus() != OrderStatus.COMPLETED) {
                System.out.println("===================================");
                System.out.println("주문 번호 : " + order.getOrderId());
                System.out.println("주문 시간 : " + order.getOrderTime());
                System.out.println("주문 상태 : " + order.getStatus());
                System.out.println();
                System.out.println("--- 주문 메뉴 목록 ---");

                // 2. 안쪽 루프 : 해당 주문(order.Order)에 포함된 메뉴(order.OrderItem) 목록 순회
                for (OrderItem item : order.getItems()) {
                    System.out.println("- 메뉴 : " + item.getMenu().getMenuName() +
                            " | 온도 : " + item.getFinalTemp() +
                            " | 컵 : " + item.getFinalCup() +
                            " | 옵션 : " + item.getFinalOptions());
                }

                System.out.println("-----------------------------------");
                System.out.println("총 결제 금액 : " + order.getTotalPrice() + "원");
                System.out.println("===================================");
                System.out.println();
            }
        }
    }

    public void updateOrderStatus() throws IOException {
        Scanner sc = new Scanner(System.in);

        ArrayList<Order> availableOrderList = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() != OrderStatus.COMPLETED) {
                availableOrderList.add(order);
            }
        }

        // 상태 변경 가능한 주문이 없을 때
        if (availableOrderList.isEmpty()) {
            System.out.println("현재 상태 변경이 가능한 주문이 없습니다.");
            return;
        }

        // targetOrder 초기화
        Order targetOrder = null;

        // 주문 ID 입력받기 및 유효 검사
        while (true) {
            System.out.print("상태를 변경할 주문 ID를 입력하세요 : ");
            int orderIdInput = sc.nextInt();
            sc.nextLine();

            for (Order order : availableOrderList) {
                if (orderIdInput == order.getOrderId()) {
                    targetOrder = order;
                    break;
                }
            }

            if (targetOrder != null) {
                break;
            } else {
                System.out.println("유효하지 않은 주문 ID입니다. 다시 입력해주세요.");
            }
        }

        System.out.println(targetOrder.getOrderId() + "번 주문의 상태를 변경합니다.");

        switch (targetOrder.getStatus()) {
            case ORDER_PLACED -> {
                System.out.println("1. 준비중 (PREPARING)");
                System.out.println("2. 준비완료/픽업대기 (READY)");
                System.out.println("3. 픽업완료 (COMPLETED)");
            }
            case PREPARING -> {
                System.out.println("2. 준비완료/픽업대기 (READY)");
                System.out.println("3. 픽업완료 (COMPLETED)");
            }
            case READY -> {
                System.out.println("3. 픽업완료 (COMPLETED)");
            }
        }

        System.out.print("변경하고 싶은 상태의 번호를 입력해주세요 : ");
        int changeStatue = sc.nextInt();
        sc.nextLine();


        switch (targetOrder.getStatus()) {
            case ORDER_PLACED -> {
                if (changeStatue == 1) {
                    targetOrder.setStatus(OrderStatus.PREPARING);
                } else if (changeStatue == 2) {
                    targetOrder.setStatus((OrderStatus.READY));
                } else if (changeStatue == 3) {
                    targetOrder.setStatus(OrderStatus.COMPLETED);
                }
            }
            case PREPARING -> {
                if (changeStatue == 2) {
                    targetOrder.setStatus((OrderStatus.READY));
                } else if (changeStatue == 3) {
                    targetOrder.setStatus(OrderStatus.COMPLETED);
                }
            }
            case READY -> {
                if (changeStatue == 3) {
                    targetOrder.setStatus(OrderStatus.COMPLETED);
                }
            }
        }

        System.out.println(targetOrder.getOrderId() + "번 주문의 상태 변경이 완료되었습니다.");
        saveOrderFile();
        System.out.println();

    }

    public void showAllSales() {
        System.out.println("[전체 매출 조회]");

        int totalPrice = 0;
        for (Order order : orderList) {
            if (order.getStatus() == OrderStatus.COMPLETED) {
                totalPrice += order.getTotalPrice();
            }
        }

        System.out.println("전체 매출 : " + totalPrice + "원");
    }

    public void showMySales(int storeId) {
        String storeName = storeList.findStoreById(storeId).getStoreName();
        System.out.println("[" + storeName + " 지점 매출 조회]");

        int totalPrice = 0;
        for (Order order : orderList) {
            if (order.getStatus() == OrderStatus.COMPLETED && order.getStoreId() == storeId) {
                totalPrice += order.getTotalPrice();
            }
        }

        System.out.println(storeName + " 지점 전체 매출 : " + totalPrice + "원");
    }

    public void showSalesBySeller() {
        System.out.println("[지점별 매출 현황]");

        // 1. storeId(int)을 key로, 누적 매출액(interger)을 value로 갖는 Map 생성
        Map<Integer, Integer> salesBySeller = new HashMap<>();

        // 2. 전체 주문 목록 순회하며 COMPLETED order 객체 찾기
        for (Order order : orderList) {
            if (order.getStatus() == OrderStatus.COMPLETED) {
                // 3. salesBySeller Map에 추가
                int storeId = order.getStoreId();
                int price = order.getTotalPrice();

                Integer currentSales = salesBySeller.get(storeId);

                if (currentSales == null) {
                    salesBySeller.put(storeId, price);
                } else {
                    salesBySeller.put(storeId, price + currentSales);
                }
            }
        }

        // 4. 데이터가 모두 쌓인 Map을 출력
        System.out.println("--------------------");
        for (Map.Entry<Integer, Integer> entry : salesBySeller.entrySet()) {
            int storeId = entry.getKey();
            String storeName = storeList.findStoreById(storeId).getStoreName();
            System.out.println("- " + storeName + " : " + entry.getValue() + "원");
        }
        System.out.println("--------------------");
    }

}
