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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.UUID;

public class OrderList {
    private ArrayList<Order> orderList = new ArrayList<>();
    private MenuList menuList;
    private StoreList storeList;

    public OrderList(MenuList menuList, StoreList storeList) throws IOException {
        this.menuList = menuList;
        this.storeList = storeList;
        loadOrderFile();
    }

    public void addOrder(Order order) throws IOException {
        this.orderList.add(order);
        saveOrderFile();

    }

    public void saveOrderFile() throws IOException {
        Path orderFilePath = Constants.BASE_PATH.resolve("Orders.txt");
        FileWriter orderWriter = new FileWriter(orderFilePath.toFile());

        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            orderWriter.write(order.getOrderId().toString() + "," +
                    order.getCustomerId() + "," +
                    order.getStoreId() + "," +
                    order.getOrderTime() + "," +
                    order.getTotalPrice() + "," +
                    order.getStatus().name() + "\n");
        }
        orderWriter.close();

        Path orderItemsFilePath = Constants.BASE_PATH.resolve("Order_items.txt");
        FileWriter orderItemsWriter = new FileWriter(orderItemsFilePath.toFile());

        for (Order order : orderList) {
            for (OrderItem item : order.getItems()) {
                orderItemsWriter.write(order.getOrderId().toString() + "," +
                        item.getMenu().getMenuId().toString() + "," +
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
        Map<UUID, Order> ordersMap = new HashMap<>();
        String line;

        while ((line = orderReader.readLine()) != null) {
            String[] parts = line.split(",");
            UUID orderId = UUID.fromString(parts[0]);
            String customerId = parts[1];
            int storeId = Integer.parseInt(parts[2]);
            LocalDateTime orderTime = LocalDateTime.parse(parts[3]);
            int totalPrice = Integer.parseInt(parts[4]);
            OrderStatus status = OrderStatus.valueOf(parts[5]);

            // 임시 객체 생성자
            Order order = new Order(orderId, customerId, storeId, orderTime, totalPrice, status);
            ordersMap.put(orderId, order);

        }
        orderReader.close();

        Path orderItemsFilePath = Constants.BASE_PATH.resolve("Order_items.txt");
        BufferedReader orderItemsReader = new BufferedReader((new FileReader(orderItemsFilePath.toFile())));


        while ((line = orderItemsReader.readLine()) != null) {
            String[] parts = line.split((","));
            UUID orderId = UUID.fromString(parts[0]);
            UUID menuId = UUID.fromString(parts[1]);
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

    public ArrayList<Order> showAndGetPendingOrders() {

        // 1. 상태 변경 가능한 주문 목록 필터링
        ArrayList<Order> pendingOrders = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() != OrderStatus.COMPLETED) {
                pendingOrders.add((order));
            }
        }

        // *주문 내역이 없다면
        if (pendingOrders.isEmpty()) {
            System.out.println("현재 주문 내역이 존재하지 않습니다.");
            return pendingOrders;
        }


        System.out.println("\n--- 현재 처리 대기중인 주문 목록 ---");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 2. 필터링 된 목록을 기반으로 '임시 번호'를 붙여 출력
        for (int i = 0; i < pendingOrders.size(); i++) {
            Order order = pendingOrders.get(i);
            System.out.println("===================================");
            // UUID 대신 임시 번호 (i+1)를 출력
            System.out.println("대기 번호 : " + (i + 1));
            System.out.println("주문 시간 : " + order.getOrderTime().format(formatter));
            System.out.println("주문 상태 : " + order.getStatus());
            System.out.println("\n--- 주문 메뉴 ---");

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
        return pendingOrders;
    }

    public void updateOrderStatus() throws IOException {

        // 1. 목록 출력 및 리스트 받아오기
        ArrayList<Order> pendingOrders = showAndGetPendingOrders();

        // 2. 처리할 주문이 없으면 바로 종료
        if (pendingOrders.isEmpty()) {
            return;
        }

        Scanner sc = new Scanner(System.in);
        Order targetOrder = null;

        // 3. 번호 입력받는 로직
        while (true) {
            System.out.print("\n상태를 변경할 주문의 대기번호를 입력하세요 (취소 : 0) : ");
            int selection = sc.nextInt();
            sc.nextLine();

            if (selection == 0) {
                System.out.println("상태 변경을 취소합니다.");
                return;
            }

            if (selection >= 1 && selection <= pendingOrders.size()) {
                targetOrder = pendingOrders.get(selection - 1);
                break;
            } else {
                System.out.println("잘못된 번호입니다. 목록에 있는 번호를 다시 입력해주세요.");
            }
        }

        System.out.println("\n['" + targetOrder.getCustomerId() + "' 님의 주문] 상태를 변경합니다. (현재: " + targetOrder.getStatus() + ")");

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

        System.out.println("주문 상태 변경이 완료되었습니다.");
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
