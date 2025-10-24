import backend.menu.*;
import backend.menu.Menu;
import backend.order.Order;
import backend.order.OrderItem;
import backend.order.OrderList;
import backend.order.OrderStatus;
import backend.store.Store;
import backend.store.StoreList;
import backend.user.User;
import backend.user.UserList;
import backend.user.UserRole;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;


public class Main {
    private static StoreList storeList;
    private static UserList userList;
    private static MenuStatusList menuStatusList;
    private static MenuList menuList;
    private static OrderList orderList;
    private static MyMenu myMenu;


    public static void main(String[] args) throws IOException {
        storeList = new StoreList();
        userList = new UserList(storeList);
        menuStatusList = new MenuStatusList();
        menuList = new MenuList(menuStatusList, storeList);
        orderList = new OrderList(menuList, storeList);
        myMenu = new MyMenu();


        showMainScreen();

        Scanner sc = new Scanner(System.in);

        // 프로그램 시작
        while (true) {
            System.out.println();
            System.out.println("안녕하세요, 카페 주문 서비스입니다.");
            System.out.println(UserRole.ADMIN.getValue() + ". " + UserRole.ADMIN.getRole());
            System.out.println(UserRole.SELLER.getValue() + ". " + UserRole.SELLER.getRole());
            System.out.println(UserRole.CUSTOMER.getValue() + ". " + UserRole.CUSTOMER.getRole());
            System.out.println("4. 프로그램 종료");
            System.out.print("역할을 선택해주세요 : ");
            int roleChoice = sc.nextInt();
            sc.nextLine();

            UserRole role = UserRole.fromValue(roleChoice);
            switch (role) {
                // 관리자 모드
                case ADMIN -> {
                    if (userList.adminLogin() == null) {
                    }
                    while (true) {
                        System.out.println();
                        System.out.println("안녕하세요 관리자님, 메뉴를 선택해주세요");
                        System.out.println("1. 전체 메뉴 CRUD");
                        System.out.println("2. 판매자 계정 관리");
                        System.out.println("3. 매출 관리");
                        System.out.println("4. 지점 관리");
                        System.out.println("5. 뒤로가기");
                        System.out.print(" : ");
                        int menuChoice = sc.nextInt();
                        sc.nextLine();

                        // 1. 전체 메뉴 CRUD
                        if (menuChoice == 1) {
                            System.out.println();
                            System.out.println("[메뉴 등록 및 관리]");
                            System.out.println("1. 메뉴 등록");
                            System.out.println("2. 등록 메뉴 확인");
                            System.out.println("3. 메뉴 수정");
                            System.out.println("4. 메뉴 삭제");
                            System.out.println("5. 뒤로가기");
                            System.out.print("할 일을 선택해주세요 : ");

                            int choice = sc.nextInt();
                            sc.nextLine();
                            System.out.println();


                            // 1-1 메뉴 등록 (Create)
                            if (choice == 1) {
                                menuList.menuCreate();
                            }

                            // 1-2. 등록 메뉴 확인 (Read)
                            else if (choice == 2) {
                                menuList.showAllMenus();
                            }

                            // 1-3. 메뉴 수정
                            else if (choice == 3) {
                                System.out.println("\n[메뉴 수정]");
                                ArrayList<Menu> allMenus = menuList.showAndGetAllMenus();
                                if (allMenus.isEmpty()) {
                                    continue;
                                }

                                Menu targetMenu = null;
                                while (true) {
                                    System.out.print("수정할 메뉴의 번호를 입력하세요 (취소: 0) : ");
                                    int selection = sc.nextInt();
                                    sc.nextLine();
                                    if (selection == 0) break;
                                    if (selection >= 1 && selection <= allMenus.size()) {
                                        // 번호로 targetMenu 찾기
                                        targetMenu = allMenus.get(selection - 1);
                                        break;
                                    }
                                    System.out.println("잘못된 번호입니다.");
                                }

                                if (targetMenu != null) {
                                    // 찾은 메뉴 객체를 menuEdit 메서드에 직접 넘겨줌
                                    menuList.menuEdit(targetMenu);
                                }
                            }

                            // 1-4. 메뉴 삭제
                            else if (choice == 4) {
                                System.out.println("\n[메뉴 삭제]");
                                ArrayList<Menu> allMenus = menuList.showAndGetAllMenus();
                                if (allMenus.isEmpty()) continue;

                                Menu targetMenu = null;
                                while (true) {
                                    System.out.print("수정할 메뉴의 번호를 입력하세요 (취소: 0) : ");
                                    int selection = sc.nextInt();
                                    sc.nextLine();
                                    if (selection == 0) break;
                                    if (selection >= 1 && selection <= allMenus.size()) {
                                        // 번호로 targetMenu 찾기
                                        targetMenu = allMenus.get(selection - 1);
                                        break;
                                    }
                                    System.out.println("잘못된 번호입니다.");
                                }

                                if (targetMenu != null) {
                                    menuList.menuDelete(targetMenu);
                                }
                            }

                            // 1-5. 뒤로가기
                            else if (choice == 5) {
                            }


                        }

                        // 2. 판매자 계정 관리
                        else if (menuChoice == 2) {
                            System.out.println("[판매자 계정 관리]");
                            System.out.println("1. 판매자 계정 생성");
                            System.out.println("2. 판매자 계정 조회");
                            System.out.println("3. 판매자 계정 수정");
                            System.out.println("4. 판매자 계정 삭제");
                            System.out.println("5. 뒤로가기");
                            System.out.print(" : ");
                            int sellerMenuChoice = sc.nextInt();
                            sc.nextLine();

                            // 판매자 계정 create
                            if (sellerMenuChoice == 1) {
                                userList.registerUser(UserRole.SELLER, userList.getSellerList(), "Seller.txt");
                            }

                            // 판매자 계정 read
                            else if (sellerMenuChoice == 2) {
                                userList.sellerAccountRead();
                            }

                            // 판매자 계정 update
                            else if (sellerMenuChoice == 3) {
                                userList.sellerAccountUpdate();
                            }

                            // 판매자 계정 delete
                            else if (sellerMenuChoice == 4) {
                                userList.sellerAccountDelete();

                            }

                            // 뒤로가기
                            else if (sellerMenuChoice == 5) {
                            }
                        }

                        // 3. 매출 관리
                        else if (menuChoice == 3) {
                            System.out.println("[매출 관리]");
                            System.out.println("1. 전체 매출 조회");
                            System.out.println("2. 지점별 매출 조회");
                            System.out.print(" : ");
                            int SalesChoice = sc.nextInt();
                            sc.nextLine();

                            // 3-1. 전체 매출 조회
                            if (SalesChoice == 1) {
                                orderList.showAllSales();
                            }

                            // 3-2. 지점별 매출 조회
                            else if (SalesChoice == 2) {
                                orderList.showSalesBySeller();
                            }

                        }

                        // 4. 지점 관리
                        else if (menuChoice == 4) {
                            System.out.println("\n[지점 관리]");
                            System.out.println("1. 전체 지점 목록 조회");
                            System.out.println("2. 신규 지점 등록");
                            System.out.print(" : ");
                            int storeMenuChoice = sc.nextInt();
                            sc.nextLine();


                            // 4-1. 전체 지점 목록 조회
                            if (storeMenuChoice == 1) {
                                storeList.showAllStores();
                            }

                            // 4-2. 신규 지점 등록
                            else if (storeMenuChoice == 2) {
                                System.out.println("[신규 지점 등록]");
                                while (true) {
                                    System.out.print("신규 등록할 지점명을 입력해주세요 : ");
                                    String newStoreName = sc.nextLine();

                                    boolean success = storeList.registerNewStore(newStoreName);

                                    if (success) {
                                        break;
                                    } else {
                                        System.out.println("이미 존재하는 지점명입니다. 다른 이름을 입력해주세요.");
                                    }
                                }
                            }

                        }

                        // 5. 관리자 모드 나가기 (뒤로 가기)
                        else if (menuChoice == 5) {
                            break;
                        }
                    }
                }

                // 판매자 모드
                case SELLER -> {
                    System.out.println();
                    System.out.println("메뉴를 선택해주세요.");
                    System.out.println("1. 로그인");
                    System.out.println("2. 뒤로가기");
                    System.out.print(" : ");
                    int sellerFirstChoice = sc.nextInt();
                    sc.nextLine();

                    // 로그인
                    if (sellerFirstChoice == 1) {
                        User loggendinSeller = userList.sellerLogin();
                        if (loggendinSeller != null) {

                            int storeId = loggendinSeller.getStoreId();
                            String storeName = storeList.findStoreById(storeId).getStoreName();

                            while (true) {
                                System.out.println();
                                System.out.println("안녕하세요, " + storeName + " 지점 판매자님.");
                                System.out.println("1. 주문 관리");
                                System.out.println("2. 추천 메뉴 관리");
                                System.out.println("3. 재고 관리");
                                System.out.println("4. 매출 조회");
                                System.out.println("5. 판매 메뉴 관리");
                                System.out.println("6. 로그아웃");
                                System.out.print("할 일을 선택해주세요 : ");

                                int menuSelect = sc.nextInt();
                                sc.nextLine();
                                System.out.println();

                                // 1. 주문 관리
                                if (menuSelect == 1) {
                                    System.out.println("[주문 관리]");
                                    System.out.println("1. 주문 목록 확인");
                                    System.out.println("2. 주문 상태 변경");
                                    System.out.println("3. 뒤로가기");
                                    System.out.print(" : ");
                                    int orderMenuChoice = sc.nextInt();
                                    sc.nextLine();

                                    // 1-1. 주문 목록 확인
                                    if (orderMenuChoice == 1) {
                                        orderList.checkOrders();
                                    }

                                    // 1-2. 주문 상태 변경
                                    else if (orderMenuChoice == 2) {
                                        orderList.updateOrderStatus();
                                    }

                                    // 1-3. 뒤로가기
                                    else if (orderMenuChoice == 3) {

                                    }

                                }

                                // 2. 추천 메뉴 등록 및 관리
                                else if (menuSelect == 2) {
                                    menuList.menuRecommend();
                                }

                                // 3. 재고 관리
                                else if (menuSelect == 3) {
                                    while (true) {
                                        System.out.println();
                                        System.out.println("[재고 관리]");
                                        System.out.println("1. 재고 현황 보기");
                                        System.out.println("2. 재고 수량 변경");
                                        System.out.println("3. 판매 상태 변경");
                                        System.out.println("4. 뒤로 가기");
                                        System.out.print(" : ");

                                        int choice = sc.nextInt();
                                        sc.nextLine();

                                        // 3-1. 재고 현황 보기
                                        if (choice == 1) {
                                            menuList.showStockStatusForSeller(storeId);
                                        }

                                        // 3-2. 재고 수량 변경
                                        else if (choice == 2) {
                                            System.out.println("\n[재고 수량 변경]");

                                            // 1. 새 메서드를 호출하여 메뉴 목록을 보여주고, 리스트를 받아옴
                                            ArrayList<Menu> sellableMenus = menuList.showAndGetSellableMenus(storeId);

                                            // 판매 가능한 메뉴가 없으면 로직 종료
                                            if (sellableMenus.isEmpty()) {
                                                continue;
                                            }

                                            Menu targetMenu = null;
                                            while (true) {
                                                System.out.print("수정할 메뉴의 ID를 입력해주세요 (취소 : 0) : ");
                                                int selection = sc.nextInt();
                                                sc.nextLine();

                                                if (selection == 0) {
                                                    targetMenu = null; // 취소를 명시적으로 표시
                                                    break;
                                                }

                                                if (selection >= 1 && selection <= sellableMenus.size()) {
                                                    // 2. 입력받은 '번호'로 리스트에서 실제 Menu 객체를 찾음
                                                    targetMenu = sellableMenus.get(selection - 1);
                                                    break;
                                                } else {
                                                    System.out.println("잘못된 번호입니다. 목록에 있는 번호를 다시 입력해주세요.");
                                                }
                                            }

                                            // 사용자가 취소를 선택한 경우
                                            if (targetMenu == null) {
                                                System.out.println("재고 수량 변경을 취소했습니다.");
                                                continue;
                                            }


                                            System.out.print("수정할 재고의 수량을 입력해주세요 : ");
                                            int newStock = sc.nextInt();
                                            sc.nextLine();

                                            // 3. 찾은 Menu 객체에서 UUID를 꺼내서 사용
                                            menuStatusList.updateStock(storeId, targetMenu.getId(), newStock);
                                        }

                                        // 3-3. 판매 상태 변경
                                        else if (choice == 3) {
                                            System.out.println("\n[판매 상태 변경]");

                                            // 1. 메뉴 목록을 보여주고, 리스트를 받아옴
                                            ArrayList<Menu> sellableMenus = menuList.showAndGetSellableMenus(storeId);

                                            // 비어있다면,
                                            if (sellableMenus.isEmpty()) {
                                                continue;
                                            }

                                            Menu targetMenu = null;
                                            // 2. 임시 번호를 입력받아 targetMenu를 찾는 로직
                                            while (true) {
                                                System.out.print("수정할 메뉴의 번호를 입력해주세요 (취소: 0) : ");
                                                int selection = sc.nextInt();
                                                sc.nextLine();

                                                if (selection == 0) {
                                                    targetMenu = null;
                                                    break;
                                                }

                                                if (selection >= 1 && selection <= sellableMenus.size()) {
                                                    targetMenu = sellableMenus.get(selection - 1);
                                                    break;
                                                } else {
                                                    System.out.println("잘못된 번호입니다. 목록에 있는 번호를 다시 입력해주세요.");
                                                }
                                            }

                                            if (targetMenu == null) {
                                                System.out.println("판매 상태 변경을 취소했습니다.");
                                                continue;
                                            }

                                            System.out.println("\n['" + targetMenu.getName() + "' 메뉴의 상태를 변경합니다.");
                                            System.out.println("1. 판매 가능 (AVAILABLE)");
                                            System.out.println("2. 판매 중지 (SOLD_OUT)");
                                            System.out.print(" : ");
                                            int statusChoice = sc.nextInt();
                                            sc.nextLine();

                                            EMenuSaleStatus newstatus = null;
                                            if (statusChoice == 1) {
                                                newstatus = EMenuSaleStatus.AVAILABLE;
                                            } else if (statusChoice == 2) {
                                                newstatus = EMenuSaleStatus.SOLD_OUT;
                                            }

                                            if (newstatus != null) {
                                                // 찾은 Menu 객체에서 UUID를 꺼내 사용
                                                menuStatusList.updateStatus(storeId, targetMenu.getId(), newstatus);
                                                System.out.println("판매 상태가 '" + newstatus.getDisplayStatus() + "' (으)로 변경되었습니다.");
                                            } else {
                                                System.out.println("번호를 잘못 입력하셨습니다. 이전 메뉴로 돌아갑니다.");
                                            }

                                        }

                                        // 3-4. 재고 관리 종료
                                        else if (choice == 4) {
                                            break;
                                        } else {
                                            System.out.println("잘못된 번호 입력");
                                        }

                                    }
                                }

                                // 4. 매출 관리
                                else if (menuSelect == 4) {
                                    orderList.showMySales(storeId);
                                }

                                // 5. 판매 메뉴 관리
                                else if (menuSelect == 5) {
                                    ArrayList<Menu> allMenus = menuList.showAndGetManageableMenus(storeId);

                                    // 메뉴가 비어있다면
                                    if (allMenus.isEmpty()) {
                                        continue;
                                    }

                                    System.out.println();
                                    System.out.println("1. 판매 메뉴 등록하기");
                                    System.out.println("2. 판매 메뉴 삭제하기");
                                    System.out.println("3. 뒤로가기");
                                    System.out.print(" : ");
                                    int salesChoice = sc.nextInt();
                                    sc.nextLine();

                                    // 5-3. 뒤로가기
                                    if (salesChoice == 3) {
                                        continue;
                                    }

                                    // 5-1 or 5-2
                                    else if (salesChoice == 1 || salesChoice == 2) {
                                        // 등록,삭제 메뉴 선택에 필요한 입력 한 번에 받기
                                        Menu targetMenu = null;
                                        while (true) {
                                            System.out.print("작업할 메뉴의 번호를 입력해주세요 (취소 : 0) : ");
                                            int selection = sc.nextInt();
                                            sc.nextLine();

                                            if (selection == 0) {
                                                targetMenu = null;
                                                break;
                                            }

                                            if (selection >= 1 && selection <= allMenus.size()) {
                                                targetMenu = allMenus.get(selection - 1);
                                                break;
                                            } else {
                                                System.out.println("잘못된 번호입니다. 목록에 있는 번호를 다시 입력해주세요.");
                                            }
                                        }

                                        if (targetMenu == null) {
                                            System.out.println("작업을 취소했습니다.");
                                            continue;
                                        }

                                        // 5-1. 판매 메뉴 등록하기
                                        if (salesChoice == 1) {
                                            menuStatusList.registerMenuForSale(storeId, targetMenu.getId());
                                        }

                                        // 5-2. 판매 메뉴 삭제하기
                                        else if (salesChoice == 2) {
                                            menuStatusList.removeMenuForSale(storeId, targetMenu.getId());
                                        }
                                    }
                                }

                                // 6. 로그아웃
                                else if (menuSelect == 6) {
                                    System.out.println("로그아웃이 완료되었습니다.");
                                    break;
                                }
                            }
                        }
                    }

                    // 뒤로가기
                    else if (sellerFirstChoice == 2) {
                    }


                }

                // 구매자 모드
                case CUSTOMER -> {
                    System.out.println();
                    System.out.println("메뉴를 선택해주세요.");
                    System.out.println("1. 회원가입");
                    System.out.println("2. 로그인");
                    System.out.println("3. 뒤로가기");
                    System.out.print(" : ");
                    int customerFirstChoice = sc.nextInt();
                    sc.nextLine();

                    // 회원가입
                    if (customerFirstChoice == 1) {
                        userList.registerUser(UserRole.CUSTOMER, userList.getCustomerList(), "Customer.txt");
                    }

                    // 로그인
                    else if (customerFirstChoice == 2) {
                        User loggedInCustomer = userList.customerLogin();
                        // 로그인 성공
                        if (loggedInCustomer != null) {
                            while (true) {
                                // todo : 하드코딩 리팩토링
                                int storeId = 1;

                                System.out.println();
                                System.out.println("안녕하세요 손님, 카페 주문 서비스입니다.");
                                System.out.println("할 일을 선택해주세요.");
                                System.out.println("1. 메뉴 보기 및 주문");
                                System.out.println("2. 주문 내역 확인 ");
                                System.out.println("3. 오늘의 메뉴 확인");
                                System.out.println("4. 나만의 메뉴 (찜)");
                                System.out.println("5. 로그아웃");
                                System.out.print(" : ");

                                int cusChoice = sc.nextInt();
                                sc.nextLine();

                                // 2-1. 메뉴 보기 및 주문
                                if (cusChoice == 1) {
                                    ArrayList<Menu> orderableMenus = menuList.showAndGetOrderableMenus(storeId);

                                    // 주문할 메뉴가 없다면
                                    if (orderableMenus.isEmpty()) {
                                        continue;
                                    }

                                    // 1. 장바구니
                                    ArrayList<OrderItem> cart = new ArrayList<>();
                                    // 2. 메뉴 담기
                                    while (true) {
                                        System.out.print("주문할 메뉴의 번호를 입력해주세요 (주문 완료는 : 0 입력) : ");
                                        int menuSelection = sc.nextInt();
                                        sc.nextLine();

                                        if (menuSelection == 0) {
                                            break; // 주문 완료
                                        }

                                        if (menuSelection < 1 || menuSelection > orderableMenus.size()) {
                                            System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
                                            continue; // 잘못된 번호면 다시 입력받기
                                        }

                                        // 유효한 번호임이 보장
                                        Menu targetMenu = orderableMenus.get(menuSelection - 1);

                                        // 재고 있을 때
                                        if (menuStatusList.isAvailable(storeId, targetMenu.getId())) {
                                            OrderItem newItem = createOrderItemWithOptions(targetMenu, menuList);
                                            cart.add(newItem);
                                            System.out.println("-> '" + targetMenu.getName() + "' 메뉴가 장바구니에 담겼습니다.");
                                        }

                                        // 재고 없을 때
                                        else {
                                            System.out.println("죄송합니다. '" + targetMenu.getName() + "' 메뉴는 현재 품절입니다.");
                                        }

                                        System.out.println("--- 현재 장바구니 : " + cart.size() + "개 ---");

                                    }

                                    // 3. 주문 처리 (루프 끝난 후)
                                    if (!cart.isEmpty()) {
                                        // 3-1. 총액 계산 및 backend.order.Order 객체 생성
                                        int totalPirce = 0;
                                        for (OrderItem item : cart) {
                                            totalPirce += item.getFinalPrice(); //
                                        }

                                        Order finalOrder = new Order(loggedInCustomer.getId(), storeId, totalPirce, OrderStatus.ORDER_PLACED, cart);

                                        // 3-2. 주문 내역에 '최종 주문서 추가'
                                        orderList.addOrder(finalOrder);

                                        // 3-3. 재고 감소
                                        for (OrderItem item : cart) {
                                            menuStatusList.decreaseStock(storeId, item.getMenu().getId());
                                        }

                                        System.out.println("주문이 완료되었습니다. 대기 번호 : " + finalOrder.getWaitingNumber());
                                    }
                                }

                                // 2-2. 주문 메뉴 확인
                                if (cusChoice == 2) {
                                    orderList.checkOrders(loggedInCustomer.getId());
                                }

                                // 2-3. 오늘의 추천 메뉴 확인
                                if (cusChoice == 3) {
                                    menuList.menuRecommendRead();
                                }

                                // 2-4. 나만의 메뉴
                                if (cusChoice == 4) {
                                    System.out.println("[나만의 메뉴]");
                                    System.out.println("1. 나만의 메뉴 등록");
                                    System.out.println("2. 나만의 메뉴 확인");
                                    System.out.println("3. 나만의 메뉴 삭제");
                                    System.out.print("원하는 기능을 선택해주세요 : ");
                                    int choice = sc.nextInt();

                                    if (choice == 1) {
                                        myMenu.CreateMyManu(menuList);
                                    } else if (choice == 2) {
                                        myMenu.ReadMyMenu();
                                    } else if (choice == 3) {
                                        myMenu.DeleteMyMenu();
                                    }
                                }

                                // 2-5. 로그아웃
                                if (cusChoice == 5) {
                                    System.out.println("로그아웃이 완료되었습니다.");
                                    break;
                                }
                            }
                        }

                        // 로그인 실패
                        else {
                            System.out.println("로그인에 실패했습니다.");
                        }
                    } else if (customerFirstChoice == 3) {
                    }


                }

                // Case UNROLE
                case UNROLE -> {
                    if (roleChoice == 4) {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    } else {
                        System.out.println("정확한 번호를 입력해주세요.");
                    }
                }
            }


        }
    }

    public static OrderItem createOrderItemWithOptions(Menu menu, MenuList menuList) {
        Scanner sc = new Scanner(System.in);
        String finalOptions = "선택안함";
        int finalPrice = menu.getPrice();
        String finalTemp = "ICE";    // 기본값
        String finalCup = "일회용컵"; // 기본값

        // 1. 추가 옵션 선택 (COFFEE, LATTE, TEA 등)
        if (menu.getOption() == MenuCategory.COFFEE) {
            System.out.println("샷 옵션을 선택해주세요");
            System.out.println("1.기본(2샷) | 2.연하게(1샷) | 3.샷추가(+500원) | 4.디카페인(+1000원)");
            System.out.print(" -> 선택: ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 3) {
                finalPrice += 500;
                finalOptions = "샷추가(3샷)";
            } else if (choice == 4) {
                finalPrice += 1000;
                finalOptions = "디카페인";
            } else if (choice == 2) {
                finalOptions = "연하게(1샷)";
            } else {
                finalOptions = "기본(2샷)";
            }
        } else if (menu.getOption() == MenuCategory.LATTE) {
            System.out.println("우유 옵션을 선택해주세요");
            System.out.println("1. 일반 우유 | 2. 오트(귀리) (+1000원)");
            System.out.print(" -> 선택: ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 2) {
                finalPrice += 1000;
                finalOptions = "오트(귀리)";
            } else {
                finalOptions = "일반 우유";
            }
        } else if (menu.getOption() == MenuCategory.TEA) {
            System.out.println("물 양을 선택해주세요");
            System.out.println("1. 보통 | 2. 적게");
            System.out.print(" -> 선택: ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 2) {
                finalOptions = "물 양 적게";
            } else {
                finalOptions = "물 양 보통";
            }
        }

        // 2. 온도 선택 (COFFEE 또는 TEA 카테고리일 경우)
        if (menu.getOption() == MenuCategory.COFFEE || menu.getOption() == MenuCategory.TEA) {
            while (true) {
                System.out.println("1. HOT | 2. ICE");
                System.out.print(" -> 온도를 선택해주세요 : ");
                int choice = sc.nextInt();
                sc.nextLine();
                if (choice == 1) {
                    finalTemp = "HOT";
                    break;
                } else if (choice == 2) {
                    finalTemp = "ICE";
                    break;
                } else {
                    System.out.println("잘못된 번호입니다.");
                }
            }
        }

        // 3. 컵 선택
        while (true) {
            System.out.println("1. 매장컵 | 2. 개인컵 (-300원) | 3. 일회용컵");
            System.out.print(" -> 컵을 선택해주세요 : ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                finalCup = "매장컵";
                break;
            } else if (choice == 2) {
                finalCup = "개인컵";
                finalPrice -= 300;
                break;
            } else if (choice == 3) {
                finalCup = "일회용컵";
                break;
            } else {
                System.out.println("잘못된 번호입니다.");
            }
        }

        System.out.println("\n-> [선택 완료] " + menu.getName() + " (온도: " + finalTemp + ", 컵: " + finalCup + ", 옵션: " + finalOptions + ")");

        return new OrderItem(menu, finalPrice, finalOptions, finalCup, finalTemp);
    }

    public static void showMainScreen() {
        // === VIEW ===
        JFrame frame = new JFrame("카페 주문 프로그램");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JButton adminButton = new JButton("관리자");
        JButton sellerButton = new JButton("판매자");
        JButton customerButton = new JButton("구매자");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        panel.add(adminButton);
        panel.add(sellerButton);
        panel.add(customerButton);

        frame.add(panel);

        // === CONTROLLER ===
        adminButton.addActionListener(e -> {
            frame.dispose(); // 현재 창 닫기
            showAdminLogin(); // 관리자 로그인 창 열기
        });

        sellerButton.addActionListener(e -> {
            frame.dispose(); // 현재 창 닫기
            showSellerLogin(); // 판매자 로그인 창 열기
        });

        customerButton.addActionListener(e -> {
            frame.dispose(); // 현재 창 닫기
            showCustomerLogin(); // 구매자 로그인 창 열기
        });

        frame.setVisible(true);
    }

    /**
     * 관리자
     */
    // [관리자] 로그인 창
    public static void showAdminLogin() {
        // ======= VIEW 화면 구성 =======
        JFrame loginFrame = new JFrame("관리자 로그인");

        // 1. 입력 필드 영역
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel idLabel = new JLabel("아이디 : ");
        JTextField idField = new JTextField(15);

        JLabel pwLabel = new JLabel("비밀번호 : ");
        JPasswordField pwField = new JPasswordField(15);

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(pwLabel);
        inputPanel.add(pwField);

        // 2. 버튼 영역
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // 가로로 나란히 배치

        JButton loginButton = new JButton("로그인");
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        // 3. 프레임 구성
        loginFrame.setLayout(new BorderLayout(10, 10));
        loginFrame.add(inputPanel, BorderLayout.CENTER);
        loginFrame.add(buttonPanel, BorderLayout.SOUTH);

        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);


        // ======= CONTROLLER 로직 처리 =======
        // 로그인 버튼 동작
        loginButton.addActionListener(e -> {
            // 1. 입력값 가져오기
            String id = idField.getText();
            String password = new String(pwField.getPassword());

            // 2. 유효성 검사
            if (id == null || id.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame,
                        "아이디와 비밀번호를 모두 입력해주세요.",
                        "입력 오류",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. 로그인 시도 (Model 호출)
            User loggedInAdmin = userList.adminLogin(id, password);

            // 4. 결과 처리
            if (loggedInAdmin != null) {
                // 로그인 성공
                JOptionPane.showMessageDialog(loginFrame,
                        "환영합니다, 관리자님!",
                        "로그인 성공",
                        JOptionPane.INFORMATION_MESSAGE);
                loginFrame.dispose();
                openAdminWindow(); // 관리자 메뉴로 이동
            } else {
                // 로그인 실패
                JOptionPane.showMessageDialog(loginFrame,
                        "로그인 실패! 아이디 또는 비밇번호를 확인해주세요.",
                        "로그인 실패",
                        JOptionPane.ERROR_MESSAGE);
                pwField.setText(""); // 비밀번호 필드 초기화
                idField.requestFocus(); // 아이디 필드에 포커스
            }
        });

        // 뒤로가기 버튼 동작
        backButton.addActionListener(e -> {
            loginFrame.dispose();
            showMainScreen();
        });

        loginFrame.setVisible(true);


    }

    // [관리자] 메뉴 창
    public static void openAdminWindow() {
        // ======= view =======
        JFrame adminFrame = new JFrame("관리자 메뉴");

        JButton menuButton = new JButton("1. 전체 메뉴 CRUD");
        JButton storeButton = new JButton("2. 지점 관리");
        JButton sellerButton = new JButton("3. 판매자 계정 관리");
        JButton salesButton = new JButton("4. 매출 관리");
        JButton backButton = new JButton("로그아웃");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(menuButton);
        panel.add(storeButton);
        panel.add(sellerButton);
        panel.add(salesButton);
        panel.add(backButton);

        adminFrame.add(panel);
        adminFrame.setSize(400, 400);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // ======= CONTROLLER =======
        // 1. 메뉴 관리 버튼 동작
        menuButton.addActionListener(e -> {
            adminFrame.dispose();
            showMenuManagementScreen(); // 메뉴 관리 화면으로 이동
        });

        // 2. 지점 관리 버튼 동작
        storeButton.addActionListener(e -> {
            adminFrame.dispose();
            showStoreManagementScreen(); // 지점 관리 화면으로 이동
        });

        // 3. 판매자 계정 관리 버튼 동작
        sellerButton.addActionListener(e -> {
            adminFrame.dispose();
            showSellerManagementScreen(); // 판매자 계정 관리 화면으로 이동
        });

        // 4. 매출 관리 버튼 동작
        salesButton.addActionListener(e -> {
            adminFrame.dispose();
            showSalesViewScreen(); // 매출 관리 화면으로 이동
        });

        // 5. 로그아웃 버튼 동작
        backButton.addActionListener(e -> {
            adminFrame.dispose();
            showMainScreen();
        });

        adminFrame.setVisible(true);
    }

    // [관리자] 메뉴 관리(CURD) 창
    public static void showMenuManagementScreen() {
        // ======= VIEW =======
        JFrame menuFrame = new JFrame("메뉴 관리");

        // 1. 컬럼 이름(헤더)과 데이터 준비 (Model)
        String[] columnNames = {"ID", "이름", "가격", "카테고리"};

        ArrayList<Menu> menus = menuList.getMenus(); // menuList에서 메뉴 목록 가져오기
        Object[][] data = new Object[menus.size()][4];
        for (int i = 0; i < menus.size(); i++) {
            Menu m = menus.get(i);
            data[i][0] = m.getId().toString().substring(0, 8);
            data[i][1] = m.getName();
            data[i][2] = m.getPrice();
            data[i][3] = m.getOption().name();
        }

        // 2. JTable 생성
        JTable menuTable = new JTable(data, columnNames);

        // 3. JScrollPane에 JTable 추가 (스크롤 기능)
        JScrollPane scrollPane = new JScrollPane(menuTable);

        // 4. CRUD 버튼들
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("추가");
        JButton editButton = new JButton("수정");
        JButton deleteButton = new JButton("삭제");
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // 5. 프레임에 컴포넌트 배치
        menuFrame.setLayout(new BorderLayout(10, 10));
        menuFrame.add(new JLabel("전체 메뉴 목록", SwingConstants.CENTER), BorderLayout.NORTH);
        menuFrame.add(scrollPane, BorderLayout.CENTER);
        menuFrame.add(buttonPanel, BorderLayout.SOUTH);

        menuFrame.setSize(600, 400);
        menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // ======= CONTROLLER =======
        // 메뉴 추가
        addButton.addActionListener(e -> {
            showAddMenuDialog(menuFrame);
        });

        // 메뉴 수정
        editButton.addActionListener(e -> {
            // 1. 테이블에서 선택된 행의 인덱스를 가져옴
            int selectedRow = menuTable.getSelectedRow();

            // 2. 아무것도 선택하지 않았으면 경고
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(menuFrame, "수정할 메뉴를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. 선택된 행에 해당하는 Menu 객체를 가져옴
            Menu selectedMenu = menuList.getMenus().get(selectedRow);

            // 4. Menu 객체를 전달하여 수정 다이얼로그를 띄움
            showEditMenuDialog(menuFrame, selectedMenu);
        });

        // 메뉴 삭제
        deleteButton.addActionListener(e -> {
            // 1. 테이블에서 선택된 행의 인덱스를 가져옴
            int selectedRow = menuTable.getSelectedRow();

            // 2. 아무것도 선택하지 않았으면 경고
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(menuFrame, "삭제할 메뉴를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. 사용자에게 삭제 여부 재확인
            int confirm = JOptionPane.showConfirmDialog(
                    menuFrame,
                    "정말로 이 메뉴를 삭제하시겠습니까?",
                    "삭제 확인",
                    JOptionPane.YES_NO_OPTION);

            // 4. 예(Yes)를 선택했을 때만 삭제 로직 실행
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // 5. 선택된 행에 해당하는 Menu 객체를 가져옴
                    Menu selectedMenu = menuList.getMenus().get(selectedRow);

                    // 6. Model 호출 : 선택된 메뉴의 UUID를 전달하여 삭제
                    menuList.menuDelete(selectedMenu.getId());


                    //7. 성공 메시지
                    JOptionPane.showMessageDialog(menuFrame, "메뉴가 성공적으로 삭제되었습니다.");

                    // 8. 화면 새로고침
                    menuFrame.dispose();
                    showMenuManagementScreen();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(menuFrame, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 뒤로가기 동작
        backButton.addActionListener(e -> {
            menuFrame.dispose();
            openAdminWindow(); // 관리자 메뉴로 이동
        });

        menuFrame.setVisible(true);
    }

    // [관리자] 메뉴 Create 팝업창
    public static void showAddMenuDialog(JFrame parentFrame) {
        // ======= VIEW =======
        // 1. Dialog 생성 (parentFrame 위에 modal로 띄움)
        JDialog dialog = new JDialog(parentFrame, "새 메뉴 추가", true);

        // 2. 입력 필드
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("이름: "));
        JTextField nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("가격: "));
        JTextField priceField = new JTextField();
        inputPanel.add(priceField);


        inputPanel.add(new JLabel("카테고리:"));
        // MenuCategory Enum 값으로 JComboBox 생성
        JComboBox<MenuCategory> categoryComboBox = new JComboBox<>(MenuCategory.values());
        inputPanel.add(categoryComboBox);

        // 3. 확인, 취소 버튼
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // 4. 다이얼로그에 컴포넌트 배치
        dialog.setLayout(new BorderLayout());
        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack(); // 컴포넌트에 맞게 창 크기 자동 조절
        dialog.setLocationRelativeTo(parentFrame); // 부모 창 중앙에 위치


        // ======= CONTROLLER =======
        // 확인 버튼 동작
        okButton.addActionListener(e -> {
            try {
                // 1. 입력값 가져오기
                String name = nameField.getText();
                int price = Integer.parseInt(priceField.getText());
                MenuCategory category = (MenuCategory) categoryComboBox.getSelectedItem();

                // 2. 유효성 검사
                if (name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "이름을 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 3. 메뉴 생성 (Model 호출)
                menuList.menuCreate(name, price, category);

                // 4. 성공 처리
                JOptionPane.showMessageDialog(dialog, "메뉴가 성공적으로 추가되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose(); // 팝업 닫기

                // 메뉴 목록 화면 새로고침
                parentFrame.dispose(); // 기존 메뉴 관리 창 닫기
                showMenuManagementScreen(); // 새 정보가 반영된 메뉴 관리 창 다시 열기

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "가격은 숫자로 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 취소 버튼 동작
        cancelButton.addActionListener(e -> {
            dialog.dispose(); // 팝업창 닫기
        });


        dialog.setVisible(true);
    }

    // [관리자] 메뉴 Update 팝업창
    public static void showEditMenuDialog(JFrame parentFrame, Menu menuToEdit) {
        // ======= VIEW =======
        JDialog dialog = new JDialog(parentFrame, "메뉴 수정", true);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("이름:"));
        JTextField nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("가격:"));
        JTextField priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("카테고리:"));
        JComboBox<MenuCategory> categoryJComboBox = new JComboBox<>(MenuCategory.values());
        inputPanel.add(categoryJComboBox);

        // 전달받은 menuToEdit 객체로 입력 필드를 미리 채움
        nameField.setText(menuToEdit.getName());
        priceField.setText(String.valueOf(menuToEdit.getPrice()));
        categoryJComboBox.setSelectedItem(menuToEdit.getOption());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);

        // ======= CONTROLLER =======
        // 확인 버튼 동작
        okButton.addActionListener(e -> {
            try {
                String newName = nameField.getText();
                int newPrice = Integer.parseInt(priceField.getText());
                MenuCategory newCategory = (MenuCategory) categoryJComboBox.getSelectedItem();

                if (newName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "이름을 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Model 호출 : menuToEdit의 UUID와 새로운 정보 전달
                menuList.menuEdit(menuToEdit.getId(), newName, newPrice, newCategory);

                JOptionPane.showMessageDialog(dialog, "메뉴가 성공적으로 수정되었습니다.");
                dialog.dispose();

                // 화면 새로고침
                parentFrame.dispose();
                showMenuManagementScreen();


            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "가격은 숫자로 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 취소 버튼 동작
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);

    }

    // [관리자] 지점 관리 창
    public static void showStoreManagementScreen() {
        // ======= VIEW =======
        JFrame storeFrame = new JFrame("지점 관리");

        // 1. 컬럼 이름(헤더)과 데이터 준비 (Model)
        String[] columnNames = {"지점ID", "지점명"};

        ArrayList<Store> stores = storeList.getStores(); // storeList에서 지점 목록 가져오기
        Object[][] data = new Object[stores.size()][2];
        for (int i = 0; i < stores.size(); i++) {
            Store s = stores.get(i);
            data[i][0] = s.getStoreId();
            data[i][1] = s.getStoreName();
        }

        // 2. JTable 및 JScrollPane 생성
        JTable storeTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(storeTable);

        // 3. 버튼들
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("지점 추가");
        JButton deleteButton = new JButton("지점 삭제");
        JButton backButton = new JButton("뒤로가기");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // 4. 프레임에 컴포넌트 배치
        storeFrame.setLayout(new BorderLayout(10, 10));
        storeFrame.add(new JLabel("전체 지점 목록", SwingConstants.CENTER), BorderLayout.NORTH);
        storeFrame.add(scrollPane, BorderLayout.CENTER);
        storeFrame.add(buttonPanel, BorderLayout.SOUTH);

        storeFrame.setSize(500, 400);
        storeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // ======= CONTROLLER =======
        // 지점 추가 버튼 클릭
        addButton.addActionListener(e -> {
            showAddStoreDialog(storeFrame);
        });

        // 지점 삭제 버튼 클릭
        deleteButton.addActionListener(e -> {
            int selectedRow = storeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(storeFrame, "삭제할 지점을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(storeFrame, "정말로 이 지점을 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // 선택된 행의 첫 번째 컬럼(지점 ID) 값 가져오기
                    int storeId = (int) storeTable.getValueAt(selectedRow, 0);

                    // Model 호출
                    storeList.deleteStore(storeId);

                    JOptionPane.showMessageDialog(storeFrame, "지점이 삭제되었습니다.");

                    // 화면 새로고침
                    storeFrame.dispose();
                    showStoreManagementScreen();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(storeFrame, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // 뒤로가기 버튼 동작
        backButton.addActionListener(e -> {
            storeFrame.dispose();
            openAdminWindow();
        });

        storeFrame.setVisible(true);
    }

    // [관리자] 지점 추가 다이얼로그
    public static void showAddStoreDialog(JFrame parentFrame) {
        // ======= VIEW =======
        JDialog dialog = new JDialog(parentFrame, "새 지점 추가", true);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("지점명:"));
        JTextField nameField = new JTextField(15);
        inputPanel.add(nameField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);


        // ======= CONTORLLER =======
        // 확인 버튼 컨트롤러
        okButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                if (name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "지점명을 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Model 호출
                storeList.registerNewStoreGUI(name);

                // 성공 시
                JOptionPane.showMessageDialog(dialog, "지점이 추가되었습니다.");
                dialog.dispose();
                parentFrame.dispose();
                showStoreManagementScreen(); // 화면 새로고침

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, "이미 존재하는 지점명입니다.", "오류", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 취소 버튼 컨트롤러
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    // [관리자] 판매자 계정 관리 창
    public static void showSellerManagementScreen() {
        // ======= VIEW =======
        JFrame sellerFrame = new JFrame("판매자 계정 관리");

        // 1.컬럼 이름 (헤더)과 데이터 준비 (Model)
        String[] columnNames = {"판매자ID", "판매자 비밀번호", "담당 지점"};

        ArrayList<User> sellers = userList.getSellerList();
        Object[][] data = new Object[sellers.size()][3];
        for (int i = 0; i < sellers.size(); i++) {
            User s = sellers.get(i);
            data[i][0] = s.getId();
            data[i][1] = s.getPassword();
            data[i][2] = s.getStoreId();
        }

        // 2. JTable 및 JScrllPane 생성
        JTable sellerTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane((sellerTable));

        // 3. 버튼들
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("판매자 계정 추가");
        JButton editButton = new JButton("판매자 계정 수정");
        JButton deleteButton = new JButton("판매자 계정 삭제");
        JButton backButton = new JButton("뒤로가기");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // 4. 프레임에 컴포넌트 배치
        sellerFrame.setLayout(new BorderLayout(10, 10));
        sellerFrame.add(new JLabel("전체 판매자 계정 목록", SwingConstants.CENTER), BorderLayout.NORTH);
        sellerFrame.add(scrollPane, BorderLayout.CENTER);
        sellerFrame.add(buttonPanel, BorderLayout.SOUTH);

        sellerFrame.setSize(500, 400);
        sellerFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // ======= CONTROLLER =======
        // 계정 추가 버튼 동작
        addButton.addActionListener(e -> {
            showAddSellerDialog(sellerFrame);
        });

        // 계정 수정 버튼 동작
        editButton.addActionListener(e -> {
            // 1. 테이블에서 선택된 행의 인덱스를 가져옴
            int selectedRow = sellerTable.getSelectedRow();

            // 2. 아무것도 선택하지 않았으면 경고
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(sellerFrame, "수정할 메뉴를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. 선택된 행에 해당하는 User 객체를 가져옴
            User selectedUser = userList.getSellerList().get(selectedRow);

            // 4. Menu 객체를 전달하여 수정 다이얼로그를 띄움
            showEditSellerDialog(sellerFrame, selectedUser);
        });

        // 계정 삭제 버튼 동작
        deleteButton.addActionListener(e -> {
            int selectedRow = sellerTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(sellerFrame, "삭제할 판매자를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(sellerFrame, "정말로 이 판매자를 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // 선택된 행의 인덱스로 ArrayList에서 객체 가져오기
                    User seller = userList.getSellerList().get(selectedRow);

                    // Model 호출
                    userList.sellerAccountDelete(seller);

                    JOptionPane.showMessageDialog(sellerFrame, "판매자 계정 삭제 완료", "성공", JOptionPane.INFORMATION_MESSAGE);

                    // 화면 새로고침
                    sellerFrame.dispose();
                    showSellerManagementScreen();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(sellerFrame, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // 뒤로가기 버튼 동작
        backButton.addActionListener(e -> {
            sellerFrame.dispose();
            openAdminWindow();
        });

        sellerFrame.setVisible(true);
    }

    // [관리자] 판매자 계정 추가 다이얼로그
    public static void showAddSellerDialog(JFrame parentFrame) {
        // ======= VIEW =======
        JDialog dialog = new JDialog(parentFrame, "새 판매자 계정 추가", true);

        String[] columnNames = {"지점ID", "지점명", "배정상태"};

        ArrayList<Store> stores = storeList.getStores();
        Object[][] data = new Object[stores.size()][3];

        // 이미 배정된 지점 ID 목록을 미리 확보 - Set 사용
        java.util.Set<Integer> assignedStoreIds = new java.util.HashSet<>();
        ArrayList<User> sellerList = userList.getSellerList();
        for (User seller : sellerList) {
            assignedStoreIds.add(seller.getStoreId());
        }

        for (int i = 0; i < stores.size(); i++) {
            Store s = stores.get(i);
            String status = (assignedStoreIds.contains(s.getStoreId())) ? "[배정됨]" : "[사용가능]";
            data[i][0] = s.getStoreId();
            data[i][1] = s.getStoreName();
            data[i][2] = status;
        }

        // JTable 및 ScrollPane 생성
        JTable sellerTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(sellerTable);

        // input
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("ID:"));
        JTextField idField = new JTextField(15);
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Password:"));
        JPasswordField pwField = new JPasswordField(15);
        inputPanel.add(pwField);

        inputPanel.add(new JLabel("지정할 지점ID:"));
        JTextField storeIdField = new JTextField(15);
        inputPanel.add(storeIdField);

        // 버튼들
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("생성");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout(10, 10));
        dialog.add(inputPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);

        // ======= CONTROLLER =======
        // 생성 버튼 동작
        okButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(pwField.getPassword());
            String storeIdText = storeIdField.getText();

            // 입력 유효성 검사
            if (id == null || id.isEmpty() || password.isEmpty() || storeIdText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "모든 필드를 입력하세요", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int storeId;
            try {
                storeId = Integer.parseInt(storeIdText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "지점 ID는 숫자로 입력하세요!");
                return;
            }

            // 유효하다면 회원가입 시도 (Model 호출)
            try {
                boolean success = userList.registerSeller(id, password, storeId);

                if (success) {
                    JOptionPane.showMessageDialog(dialog, "판매자 계정 생성 성공!", "성공", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    showSellerManagementScreen();
                } else {
                    JOptionPane.showMessageDialog(dialog, "이미 존재하는 ID입니다. 다시 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    idField.setText("");
                    idField.requestFocus();
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "입력 오류", JOptionPane.ERROR_MESSAGE);
                storeIdField.setText("");
                storeIdField.requestFocus();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog, "파일 저장 중 오류가 발생했습니다", "오류", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // 취소 버튼 동작
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);

    }

    // [관리자] 판매자 계정 수정 다이얼로그
    public static void showEditSellerDialog(JFrame parentFrame, User sellerToEdit) {
        // ======= VIEW =======
        JDialog dialog = new JDialog(parentFrame, "판매자 계정 수정", true);

        String[] columnNames = {"지점ID", "지점명", "배정상태"};

        ArrayList<Store> stores = storeList.getStores();
        Object[][] data = new Object[stores.size()][3];

        // 이미 배정된 지점 ID 목록 - Set 사용 (단, 현재 수정중인 판매자의 지점은 제외)
        java.util.Set<Integer> assignedStoreIds = new java.util.HashSet<>();
        ArrayList<User> sellerList = userList.getSellerList();
        for (User seller : sellerList) {
            if (!seller.getId().equals(sellerToEdit.getId())) {
                assignedStoreIds.add(seller.getStoreId());
            }
        }

        for (int i = 0; i < stores.size(); i++) {
            Store s = stores.get(i);
            // 현재 판매자의 지점이면 [현재 담당] 표시
            String status;
            if (s.getStoreId() == sellerToEdit.getStoreId()) {
                status = "[현재 담당]";
            } else if (assignedStoreIds.contains(s.getStoreId())) {
                status = "[배정됨]";
            } else {
                status = "[사용가능]";
            }
            data[i][0] = s.getStoreId();
            data[i][1] = s.getStoreName();
            data[i][2] = status;
        }

        // JTable 및 ScrollPane 생성
        JTable sellerTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(sellerTable);

        // input
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ID는 수정 불가 (비활성화)
        inputPanel.add(new JLabel("ID:"));
        JTextField idField = new JTextField();
        idField.setEditable(false); // 편집 불가
        idField.setBackground(Color.LIGHT_GRAY);
        inputPanel.add(idField);
        idField.setText(sellerToEdit.getId());

        inputPanel.add(new JLabel("Password:"));
        JPasswordField pwField = new JPasswordField();
        pwField.setText(sellerToEdit.getPassword());
        inputPanel.add(pwField);

        inputPanel.add(new JLabel("지점ID:"));
        JTextField storeIdField = new JTextField();
        storeIdField.setText(String.valueOf(sellerToEdit.getStoreId()));
        inputPanel.add(storeIdField);

        // 버튼들
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(inputPanel, BorderLayout.NORTH);
        dialog.add(sellerTable, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);

        // ======= CONTROLLER =======
        // 확인 버튼 동작
        okButton.addActionListener(e -> {

            try {
                String id = idField.getText(); // 비활성화
                String newPassword = new String(pwField.getPassword());
                int newStoreId = Integer.parseInt(storeIdField.getText());

                // 유효성 검사
                if (newPassword.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "비밀번호를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (newStoreId <= 0) {
                    JOptionPane.showMessageDialog(dialog, "지점 ID는 1 이상이여야 합니다.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Model 호출
                userList.sellerAccountUpdate(id, newPassword, newStoreId);

                JOptionPane.showMessageDialog(dialog, "판매자 계정 수정 완료");
                dialog.dispose();

                // 화면 새로고침
                parentFrame.dispose();
                showSellerManagementScreen();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "지점 ID는 정수로 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                storeIdField.requestFocus();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "입력 오류", JOptionPane.ERROR_MESSAGE);
                storeIdField.setText(String.valueOf(sellerToEdit.getStoreId())); // 원래 값으로 복원
                storeIdField.requestFocus();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        // 취소 버튼 동작
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    // [관리자] 매출 조회 창
    public static void showSalesViewScreen() {
        // ======= VIEW =======
        JFrame frame = new JFrame("매출 조회");

        // 상단 : 조회 방식 선택
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("조회 방식:"));

        String[] viewOptions = {"전체 매출", "지점별 매출"};
        JComboBox<String> viewComboBox = new JComboBox<>(viewOptions);
        topPanel.add(viewComboBox);


        // 지점 선택 콤보박스 (초기에는 숨김)
        JLabel storeLabel = new JLabel("지점:");
        storeLabel.setVisible(false);

        JComboBox<String> storeComboBox = new JComboBox<>(); // 지점 선택용
        storeComboBox.setVisible(false); // 처음엔 숨김

        // 지점 목록 채우기
        ArrayList<Store> stores = storeList.getStores();
        for (Store store : stores) {
            storeComboBox.addItem(store.getStoreId() + " - " + store.getStoreName());
        }

        topPanel.add(storeLabel);
        topPanel.add(storeComboBox);

        // 중간 : JTable로 매출 데이터 표시
        String[] columnNames = {"지점명", "주문 수 ", "총 매출"};

        // 초기 데이터 : 전체 매출
        Object[][] initalData = orderList.getSalesDataForTable(storeList);
        JTable salesTable = new JTable(initalData, columnNames);
        JScrollPane scrollPane = new JScrollPane(salesTable);

        // 하단 : 뒤로가기 버튼
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("뒤로가기");
        buttonPanel.add(backButton);

        // 레이아웃 구성
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // ======= CONTROLLER =======
        // 조회 방식 변경 이벤트
        viewComboBox.addActionListener(e -> {
            String selected = (String) viewComboBox.getSelectedItem();

            if ("지점별 매출".equals(selected)) {
                // 지점 선택 콤보박스 표시
                storeLabel.setVisible(true);
                storeComboBox.setVisible(true);

                // 첫 번째 지점의 매출 표시
                if (storeComboBox.getItemCount() > 0) {
                    String selectedStore = (String) storeComboBox.getSelectedItem();
                    int storeId = Integer.parseInt(selectedStore.split(" - ")[0]);

                    Object[][] data = orderList.getSalesDataByStore(storeId, storeList);
                    salesTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                }
            } else {
                // 전체 매출 표시
                storeLabel.setVisible(false);
                storeComboBox.setVisible(false);

                Object[][] data = orderList.getSalesDataForTable(storeList);
                salesTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
            }

            frame.revalidate();
            frame.repaint();
        });

        // 지점 선택 변경 이벤트
        storeComboBox.addActionListener(e -> {
            if (storeComboBox.isVisible() && storeComboBox.getSelectedItem() != null) {
                String selectedStore = (String) storeComboBox.getSelectedItem();
                int storeId = Integer.parseInt(selectedStore.split(" - ")[0]);

                Object[][] data = orderList.getSalesDataByStore(storeId, storeList);
                salesTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
            }
        });

        // 뒤로가기 버튼
        backButton.addActionListener(e -> {
            frame.dispose();
            openAdminWindow();
        });

        frame.setVisible(true);
    }

    /**
     * 판매자
     */
    // [판매자] 로그인 창
    public static void showSellerLogin() {
        // ======= VIEW 화면 구성 =======
        JFrame loginFrame = new JFrame("판매자 로그인");

        // 1. 입력 필드 영역
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel idLabel = new JLabel("아이디 : ");
        JTextField idField = new JTextField(15);

        JLabel pwLabel = new JLabel("비밀번호 : ");
        JPasswordField pwField = new JPasswordField(15);

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(pwLabel);
        inputPanel.add(pwField);

        // 2. 버튼 영역
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton loginButton = new JButton("로그인");
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        // 3. 프레임 구성
        loginFrame.setLayout(new BorderLayout(10, 10));
        loginFrame.add(inputPanel, BorderLayout.CENTER);
        loginFrame.add(buttonPanel, BorderLayout.SOUTH);

        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);


        // ======= CONTROLLER 로직 처리 =======
        loginButton.addActionListener(e -> {
            // 1. 입력값 가져오기
            String id = idField.getText();
            String password = new String(pwField.getPassword());

            // 2. 유효성 검사
            if (id.trim().isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame,
                        "아이디와 비밀번호를 모두 입력해주세요.",
                        "입력 오류",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. 로그인 시도 (Model 호출)
            User loggedInSeller = userList.sellerLogin(id, password);

            // 4. 결과 처리
            if (loggedInSeller != null) {
                // 로그인 성공
                // 4-1. 지점 정보 가져오기
                int storeId = loggedInSeller.getStoreId();
                String storeName = storeList.findStoreById(storeId).getStoreName();

                // 2. 환영 메세지 (지점명 포함)
                JOptionPane.showMessageDialog(loginFrame,
                        "환영합니다, " + storeName + " 지점 판매자님!");

                // 3. 판매자 메뉴로 이동 (loggedInSeller 객체 넘김)
                loginFrame.dispose();
                openSellerWindow(loggedInSeller);
            } else {
                // 로그인 실패
                JOptionPane.showMessageDialog(loginFrame,
                        "로그인 실패! 아이디 또는 비밀번호를 확인해주세요.",
                        "로그인 실패",
                        JOptionPane.ERROR_MESSAGE);
                pwField.setText(""); // 비밀번호 필드 초기화
                idField.requestFocus(); // 아이디 필드에 포커스
            }
        });

        // 뒤로가기 버튼 동작
        backButton.addActionListener(e -> {
            loginFrame.dispose();
            showMainScreen();
        });

        loginFrame.setVisible(true);
    }

    // [판매자] 메뉴 창
    public static void openSellerWindow(User loggedInSeller) {
        // ======= VIEW =======
        JFrame sellerFrame = new JFrame("판매자 메뉴 - " + loggedInSeller.getId());

        JButton orderButton = new JButton("1. 주문 관리");
        JButton stockButton = new JButton("2. 재고 관리");
        JButton menuButton = new JButton("3. 판매 메뉴 관리");
        JButton salesButton = new JButton("4. 매출 조회");
        JButton recommendButton = new JButton("5. 추천 메뉴 관리");
        JButton backButton = new JButton("로그아웃");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(orderButton);
        panel.add(stockButton);
        panel.add(menuButton);
        panel.add(salesButton);
        panel.add(recommendButton);
        panel.add(backButton);

        sellerFrame.add(panel);
        sellerFrame.setSize(400, 400);
        sellerFrame.setLocationRelativeTo(null);
        sellerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ======= CONTROLLER =======
        // 1. 주문 관리 버튼
        orderButton.addActionListener(e -> {
            sellerFrame.dispose();
            showOrderManagementScreen(loggedInSeller);
        });

        // 2. 재고 관리 버튼
        stockButton.addActionListener(e -> {
            sellerFrame.dispose();
            showStockManagementScreen(loggedInSeller);
        });

        // 3. 판매 메뉴 관리 버튼
        menuButton.addActionListener(e -> {
            sellerFrame.dispose();
            showSellingMenuManagementScreen(loggedInSeller);
        });

        // 4. 매출 조회 버튼
        salesButton.addActionListener(e -> {
            sellerFrame.dispose();
            showSalesViewScreen(loggedInSeller);
        });

        // 5. 추천 메뉴 관리 버튼
        recommendButton.addActionListener(e -> {
            sellerFrame.dispose();
            showRecommendMenuManagementScreen(loggedInSeller);
        });

        // 로그 아웃 버튼
        backButton.addActionListener(e -> {
            sellerFrame.dispose();
            showMainScreen();
        });


        sellerFrame.setVisible(true);
    }

    // [판매자] 1. 주문 관리 화면
    public static void showOrderManagementScreen(User seller) {
        int storeId = seller.getStoreId();
        String storeName = storeList.findStoreById(storeId).getStoreName();

        // === VIEW ===
        JFrame frame = new JFrame("주문 관리 - " + storeName);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 진행 중인 주문 가져오기 (전체 주문 중, COMPLETED가 아닌 것)
        ArrayList<Order> allPendingOrders = orderList.getPendingOrders();

        // 본인 지점 주문만 필터링
        ArrayList<Order> myStorePendingOrders = new ArrayList<>();
        for (Order order : allPendingOrders) {
            if (order.getStoreId() == storeId) {
                myStorePendingOrders.add(order);
            }
        }

        // 주문이 없을 때 처리
        if (myStorePendingOrders.isEmpty()) {
            JLabel messageLabel = new JLabel("현재 처리할 주문이 없습니다.");
            messageLabel.setHorizontalAlignment(JLabel.CENTER);

            JPanel emptyPanel = new JPanel(new GridLayout(2, 1));
            emptyPanel.add(messageLabel);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton backButton = new JButton("뒤로가기");
            buttonPanel.add(backButton);
            emptyPanel.add(buttonPanel);

            frame.add(emptyPanel, BorderLayout.CENTER);

            backButton.addActionListener(e -> {
                frame.dispose();
                openSellerWindow(seller);
            });

            frame.setVisible(true);
            return;
        }

        // JTable 데이터 준비
        String[] columnNames = {"대기번호", "고객ID", "메뉴", "수량", "주문상태", "주문시간"};
        Object[][] data = new Object[myStorePendingOrders.size()][6];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < myStorePendingOrders.size(); i++) {
            Order order = myStorePendingOrders.get(i);
            data[i][0] = order.getWaitingNumber();
            data[i][1] = order.getCustomerId();

            // 메뉴명 (여러 개면 "아메리카노 외 2건")
            if (order.getItems().size() == 1) {
                data[i][2] = order.getItems().get(0).getMenu().getName();
            } else {
                data[i][2] = order.getItems().get(0).getMenu().getName() + " 외" + (order.getItems().size() - 1) + "건";
            }

            data[i][3] = order.getItems().size();
            data[i][4] = order.getStatus().toString();
            data[i][5] = order.getOrderTime().format(formatter);
        }

        JTable orderTable = new JTable(data, columnNames);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton detailButton = new JButton("상세 보기");
        JButton changeButton = new JButton("상태 변경");
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(detailButton);
        buttonPanel.add(changeButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // === CONTROLLER ===
        // 상세 보기 버튼
        detailButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "주문을 선택해주세요.", "알림", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Order selectedOrder = myStorePendingOrders.get(selectedRow);
            showOrderDetailDialog(frame, selectedOrder);
        });

        // 상태 변경 버튼
        changeButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "주문을 선택해주세요!", "알림", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Order selectedOrder = myStorePendingOrders.get(selectedRow);
            showOrderStatusDialog(frame, seller, selectedOrder);
        });

        // 뒤로가기 버튼
        backButton.addActionListener(e -> {
            frame.dispose();
            openSellerWindow(seller);
        });

        frame.setVisible(true);
    }

    // [판매자] 1-1. 주문 상태 변경 다이얼로그
    public static void showOrderStatusDialog(JFrame parentFrame, User seller, Order order) {
        // === VIEW ===
        JDialog dialog = new JDialog(parentFrame, "주문 상태 변경", true);
        dialog.setSize(450, 400);
        dialog.setLayout(new BorderLayout(10, 10));

        // 상단 정보 패널
        JPanel infoPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        infoPanel.add(new JLabel("대기번호: " + order.getWaitingNumber()));
        infoPanel.add(new JLabel("고객 ID: " + order.getCustomerId()));
        infoPanel.add(new JLabel("현재 상태: " + order.getStatus().toString()));
        infoPanel.add(new JLabel("")); // 간격
        infoPanel.add(new JLabel("--- 주문 메뉴 ---"));

        // 주문 메뉴 목록 표시
        StringBuilder menuInfo = new StringBuilder("<html>");
        for (OrderItem item : order.getItems()) {
            menuInfo.append(item.getMenu().getName())
                    .append(" (")
                    .append(item.getFinalTemp())
                    .append(", ")
                    .append(item.getFinalCup())
                    .append(")<br>");
        }
        menuInfo.append("</html>");
        infoPanel.add(new JLabel(menuInfo.toString()));

        dialog.add(infoPanel, BorderLayout.NORTH);

        // 중앙 선택 패널
        JPanel selectPanel = new JPanel(new BorderLayout(5, 5));
        selectPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));

        JLabel label = new JLabel("변경할 상태를 선택해주세요:");
        selectPanel.add(label, BorderLayout.NORTH);

        // 현재 상태에 따라 다음 상태 옵션 생성
        String[] statusOptions = getNextStatusOptions(order.getStatus());
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
        selectPanel.add(statusCombo, BorderLayout.CENTER);

        dialog.add(selectPanel, BorderLayout.CENTER);

        // 하단 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton confirmButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // === CONTORLLER ===
        // 확인 버튼 동작
        confirmButton.addActionListener(e -> {
            try {
                int selectedIndex = statusCombo.getSelectedIndex();
                OrderStatus newStatus = getStatusFromSelection(order.getStatus(), selectedIndex);

                if (newStatus == null) {
                    JOptionPane.showMessageDialog(dialog,
                            "올바른 상태를 선택해주세요!",
                            "오류",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 상태 변경
                order.setStatus(newStatus);
                orderList.saveOrderFile();

                JOptionPane.showMessageDialog(dialog,
                        "주문 상태가 '" + newStatus.toString() + "'(으)로 변경되었습니다!");

                dialog.dispose();
                parentFrame.dispose();
                showOrderManagementScreen(seller);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "상태 변경 중 오류가 발생했습니다: " + ex.getMessage(),
                        "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 취소 버튼 동작
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);

    }

    // [판매자] 1-1-1. 헬퍼 메서드 : 현재 상태에 따라 변경 가능한 다음 상태 옵션 반환
    private static String[] getNextStatusOptions(OrderStatus currentStatus) {
        return switch (currentStatus) {
            case ORDER_PLACED -> new String[]{"준비중 (PREPARING)", "준비완료/픽업대기 (READY)", "픽업완료 (COMPLETED)"};
            case PREPARING -> new String[]{"준비완료/픽업대기 (READY)", "픽업완료 (COMPLETED)"};
            case READY -> new String[]{"픽업완료 (COMPLETED)"};
            case COMPLETED -> new String[]{"이미 완료된 주문입니다"};
        };
    }

    // [판매자] 1-1-2. 헬퍼 메서드 : 선택된 인덱스와 현재 상태를 기반으로 새 상태 반환
    private static OrderStatus getStatusFromSelection(OrderStatus currentStatus, int selectedIndex) {
        return switch (currentStatus) {
            case ORDER_PLACED -> {
                if (selectedIndex == 0) yield OrderStatus.PREPARING;
                if (selectedIndex == 1) yield OrderStatus.READY;
                if (selectedIndex == 2) yield OrderStatus.COMPLETED;
                yield null;
            }
            case PREPARING -> {
                if (selectedIndex == 0) yield OrderStatus.READY;
                if (selectedIndex == 1) yield OrderStatus.COMPLETED;
                yield null;
            }
            case READY -> {
                if (selectedIndex == 0) yield OrderStatus.COMPLETED;
                yield null;
            }
            case COMPLETED -> null;
        };
    }

    // [판매자] 1-2. 주문 상세 보기 다이이얼로그
    public static void showOrderDetailDialog(JFrame parentFrame, Order order) {
        // === VIEW ===
        JDialog dialog = new JDialog(parentFrame, "주문 상세 정보", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout(10, 10));

        // 상단 주문 정보
        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        infoPanel.add(new JLabel("대기번호: " + order.getWaitingNumber()));
        infoPanel.add(new JLabel("고객 ID: " + order.getCustomerId()));
        infoPanel.add(new JLabel("주문 상태: " + order.getStatus()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        infoPanel.add(new JLabel("주문 시간: " + order.getOrderTime().format(formatter)));
        infoPanel.add(new JLabel("총 결제 금액: " + order.getTotalPrice() + "원"));
        dialog.add(infoPanel, BorderLayout.NORTH);

        // 중앙 메뉴 목록 (JTable)
        String[] columnNames = {"메뉴명", "온도", "컵", "옵션", "가격"};
        Object[][] data = new Object[order.getItems().size()][5];

        for (int i = 0; i < order.getItems().size(); i++) {
            OrderItem item = order.getItems().get(i);
            data[i][0] = item.getMenu().getName();
            data[i][1] = item.getFinalTemp();
            data[i][2] = item.getFinalCup();
            data[i][3] = item.getFinalOptions();
            data[i][4] = item.getFinalPrice() + "원";
        }

        JTable menuTable = new JTable(data, columnNames);
        menuTable.setRowHeight(25);
        menuTable.setEnabled(false); // 읽기 전용
        JScrollPane scrollPane = new JScrollPane(menuTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // 하단 닫기 버튼
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("닫기");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    // [판매자] 2. 재고 관리 화면
    public static void showStockManagementScreen(User seller) {
        // ======= VIEW =======
        JFrame frame = new JFrame("재고 관리 - " + seller.getId());

        int storeId = seller.getStoreId();

        // 1. 현재 판매 중인 메뉴 목록 가져오기 (AVAILABLE 상태인 메뉴만)
        ArrayList<Menu> sellableMenus = new ArrayList<>();
        ArrayList<MenuStatus> menuStatusesForStore = new ArrayList<>();

        for (Menu menu : menuList.getMenus()) {
            MenuStatus status = menuStatusList.findMenuStatus(storeId, menu.getId());
            // 판매 메뉴로 등록된 것만 (status가 null이 아닌 것)
            if (status != null) {
                sellableMenus.add(menu);
                menuStatusesForStore.add(status);
            }
        }

        // 2. 판매 중인 메뉴가 없음켠 안내
        if (sellableMenus.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "판매 중인 메뉴가 없습니다. \n 먼저 '판매 메뉴 관리'에서 메뉴를 등록해주세요.",
                    "알림",
                    JOptionPane.INFORMATION_MESSAGE);
            openSellerWindow(seller);
            return;
        }

        // 3. JTable용 데이터 준비
        String[] columnNames = {"메뉴명", "재고 수량", "판매 상태", "수정"};
        Object[][] data = new Object[sellableMenus.size()][4];

        for (int i = 0; i < sellableMenus.size(); i++) {
            Menu menu = sellableMenus.get(i);
            MenuStatus status = menuStatusesForStore.get(i);

            data[i][0] = menu.getName();
            data[i][1] = status.getStock() + "개";
            data[i][2] = status.getStatus().getDisplayStatus();
            data[i][3] = "수정"; // 버튼 텍스트
        }

        // 4. JTable 생성
        JTable stockTable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 직접 편집 불가 (수정 버튼으로만 수정)
            }
        };

        JScrollPane scrollPane = new JScrollPane(stockTable);

        // 5. 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton editButton = new JButton("재고/상태 수정");
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(editButton);
        buttonPanel.add(backButton);

        // 6. 레이아웃
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(new JLabel("판매 중인 메뉴의 재고 및 상태 관리", SwingConstants.CENTER), BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // ======= CONTORLLER =======
        // 재고/상태 수정 버튼
        editButton.addActionListener(e -> {
            int selectedRow = stockTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame,
                        "수정할 메뉴를 선택해주세요.",
                        "알림",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 선택한 메뉴와 상태 정보 가져오기
            Menu selectedMenu = sellableMenus.get(selectedRow);
            MenuStatus selcectedStatus = menuStatusesForStore.get(selectedRow);

            // 수정 다이얼로그 열기
            showStockEditDialog(frame, seller, selectedMenu, selcectedStatus);
        });

        // 뒤로가기 버튼
        backButton.addActionListener(e -> {
            frame.dispose();
            openSellerWindow(seller);
        });

        frame.setVisible(true);

    }

    // [판매자] 2-1. 재고/상태 수정 다이얼로그
    public static void showStockEditDialog(JFrame parentFrame, User seller, Menu menu, MenuStatus menuStatus) {
        // ======= VIEW =======
        JDialog dialog = new JDialog(parentFrame, "재고/상태 수정 - " + menu.getName(), true);

        // 입력 패널
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // 메뉴명 (읽기 전용)
        inputPanel.add(new JLabel("메뉴명:"));
        JLabel menuNameLabel = new JLabel(menu.getName());
        menuNameLabel.setFont(new Font(menuNameLabel.getFont().getName(), Font.BOLD, 12));
        inputPanel.add(menuNameLabel);

        // 재고 수량
        inputPanel.add(new JLabel("재고 수량:"));
        JTextField stockField = new JTextField(String.valueOf(menuStatus.getStock()));
        inputPanel.add(stockField);

        // 판매 상태
        inputPanel.add(new JLabel("판매 상태:"));
        String[] statusOptions = {"판매중 (AVAILABLE)", "품절 (SOLD_OUT)"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);

        // 현재 상태로 초기화
        if (menuStatus.getStatus() == EMenuSaleStatus.AVAILABLE) {
            statusComboBox.setSelectedIndex(0);
        } else {
            statusComboBox.setSelectedIndex(1);
        }
        inputPanel.add(statusComboBox);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("저장");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // 레이아웃
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);

        // ======= CONTROLLER =======
        // 저장 버튼
        saveButton.addActionListener(e -> {
            try {
                // 1. 재고 수량 읽기
                int newStock;
                try {
                    newStock = Integer.parseInt(stockField.getText().trim());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "재고 수량은 숫자로 입력해주세요.",
                            "입력 오류",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (newStock < 0) {
                    JOptionPane.showMessageDialog(dialog,
                            "재고 수량은 0 이상이여야 합니다.",
                            "입력 오류",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 2. 판매 상태 읽기
                int statusIndex = statusComboBox.getSelectedIndex();
                EMenuSaleStatus newStatus = (statusIndex == 0) ? EMenuSaleStatus.AVAILABLE : EMenuSaleStatus.SOLD_OUT;

                // 3. Model 호출 - 재고 수량 업데이트
                menuStatusList.updateStock(seller.getStoreId(), menu.getId(), newStock);

                // 4. Model 호출 - 판매 상태 업데이트
                menuStatusList.updateStatus(seller.getStoreId(), menu.getId(), newStatus);

                JOptionPane.showMessageDialog(dialog,
                        "재고 및 상태가 수정되었습니다!",
                        "성공",
                        JOptionPane.INFORMATION_MESSAGE);

                dialog.dispose();

                // 화면 새로고침
                parentFrame.dispose();
                showStockManagementScreen(seller);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "파일 저장 중 오류가 발생했습니다.",
                        "오류",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // 취소 버튼
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    // [판매자] 3. 판매 메뉴 관리 화면
    public static void showSellingMenuManagementScreen(User seller) {
        // 전체 메뉴 중 판매할 메뉴 선택
        // ======= VIEW =======
        JFrame frame = new JFrame("판매 메뉴 관리 - " + seller.getId());

        int storeId = seller.getStoreId();

        // 1. 관리 가능한 메뉴 목록 가져오기
        ArrayList<Menu> allMenus = menuList.getManageableMenus();

        if (allMenus.isEmpty()) {
            JOptionPane.showMessageDialog(null, "등록된 메뉴가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            openSellerWindow(seller);
            return;
        }

        // 2. 현재 이 지점에서 판매중인 메뉴 ID 목록 가져오기
        Set<UUID> sellingMenuIds = new HashSet<>();
        for (MenuStatus ms : menuStatusList.getMenuStatuses()) {
            if (ms.getStoreId() == storeId) {
                sellingMenuIds.add(ms.getMenuId());
            }
        }

        // 3. JTable용 데이터 준비 (체크박스 포함)
        String[] columnNames = {"판매중", "메뉴명", "가격", "카테고리"};
        Object[][] data = new Object[allMenus.size()][4];

        for (int i = 0; i < allMenus.size(); i++) {
            Menu menu = allMenus.get(i);
            data[i][0] = sellingMenuIds.contains(menu.getId()); // 체크박스
            data[i][1] = menu.getName();
            data[i][2] = menu.getPrice() + "원";
            data[i][3] = menu.getOption();
        }

        // 4. JTable 생성
        JTable menuTable = new JTable(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return Boolean.class; // 체크박스
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // 체크박스만 편집 가능
            }
        };

        JScrollPane scrollPane = new JScrollPane(menuTable);

        // 5. 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton applyButton = new JButton("적용하기");
        JButton selectAllButton = new JButton("전체 선택");
        JButton deselectAllButton = new JButton("전체 해제");
        JButton backBButton = new JButton("뒤로가기");

        buttonPanel.add(applyButton);
        buttonPanel.add(selectAllButton);
        buttonPanel.add(deselectAllButton);
        buttonPanel.add(backBButton);

        // 6. 레이아웃
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(new JLabel("판매할 메뉴를 선택하세요 (체크: 판매중, 미체크: 판매 안 함)", SwingConstants.CENTER), BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // ======= CONTROLLER =======
        // 적용하기 버튼
        applyButton.addActionListener(e -> {
            try {
                // 변경 전 상태 저장
                Set<UUID> oldSellingMenuIds = new HashSet<>(sellingMenuIds);

                // 변경 후 상태 읽기
                Set<UUID> newSellingMenuIds = new HashSet<>();
                for (int i = 0; i < menuTable.getRowCount(); i++) {
                    Boolean isChecked = (Boolean) menuTable.getValueAt(i, 0);
                    if (isChecked != null && isChecked) {
                        UUID menuId = allMenus.get(i).getId();
                        newSellingMenuIds.add(menuId);
                    }
                }

                // 신규 등록할 메뉴 (체크되었는데 기존에 없던 것)
                for (UUID menuId : newSellingMenuIds) {
                    if (!oldSellingMenuIds.contains(menuId)) {
                        menuStatusList.registerMenuForSale(storeId, menuId);
                    }
                }

                // 삭제할 메뉴 (체크 해제되었는데 기존에 없던 것)
                for (UUID menuId : oldSellingMenuIds) {
                    if (!newSellingMenuIds.contains(menuId)) {
                        menuStatusList.removeMenuForSale(storeId, menuId);

                    }
                }

                JOptionPane.showMessageDialog(frame, "판매 메뉴가 업데이트되었습니다!", "성공", JOptionPane.INFORMATION_MESSAGE);

                // 화면 새로 고침
                frame.dispose();
                showSellingMenuManagementScreen(seller);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // 전체 선택 버튼
        selectAllButton.addActionListener(e -> {
            for (int i = 0; i < menuTable.getRowCount(); i++) {
                menuTable.setValueAt(true, i, 0);
            }
        });

        // 전체 해제 버튼
        deselectAllButton.addActionListener(e -> {
            for (int i = 0; i < menuTable.getRowCount(); i++) {
                menuTable.setValueAt(false, i, 0);
            }
        });

        // 뒤로가기 버튼
        backBButton.addActionListener(e -> {
            frame.dispose();
            openSellerWindow(seller);
        });

        frame.setVisible(true);
    }

    // [판매자] 4. 매출 조회 화면
    public static void showSalesViewScreen(User seller) {
        int storeId = seller.getStoreId();
        String storeName = storeList.findStoreById(storeId).getStoreName();

        // === VIEW ===
        JFrame frame = new JFrame("매출 조회 - " + storeName);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // 완료된 주문 가져오기
        ArrayList<Order> completedOrders = orderList.getCompletedOrdersByStore(storeId);

        // 매출 데이터 계산
        int totalSales = 0;
        for (Order order : completedOrders) {
            totalSales += order.getTotalPrice();
        }

        int completedOrderCount = completedOrders.size();
        int avgOrderAmount = (completedOrderCount > 0) ? (totalSales / completedOrderCount) : 0;

        // 상단 요약 패널
        JPanel summaryPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        Font summayFont = new Font("맑은 고딕", Font.BOLD, 16);

        JLabel totalLabel = new JLabel("총 매출액: " + String.format("%,d", totalSales) + "원", JLabel.CENTER);
        totalLabel.setFont(summayFont);
        summaryPanel.add(totalLabel);

        JLabel countLabel = new JLabel("완료된 주문 수: " + completedOrderCount + "건", JLabel.CENTER);
        countLabel.setFont(summayFont);
        summaryPanel.add(countLabel);

        JLabel avgLabel = new JLabel("평균 주문 금액: " + String.format("%,d", avgOrderAmount) + "원", JLabel.CENTER);
        avgLabel.setFont(summayFont);
        summaryPanel.add(avgLabel);

        summaryPanel.add(new JLabel("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", JLabel.CENTER));

        frame.add(summaryPanel, BorderLayout.NORTH);

        // 중앙 주문 내역 테이블
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));

        JLabel tableTitle = new JLabel("완료된 주문 내역");
        tableTitle.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        tablePanel.add(tableTitle, BorderLayout.NORTH);

        String[] columnNames = {"대기번호", "고객ID", "메뉴", "금액", "주문시간"};
        Object[][] data = new Object[completedOrders.size()][5];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");

        for (int i = 0; i < completedOrders.size(); i++) {
            Order order = completedOrders.get(i);
            data[i][0] = order.getWaitingNumber();
            data[i][1] = order.getCustomerId();

            // 메뉴명
            if (order.getItems().size() == 1) {
                data[i][2] = order.getItems().get(0).getMenu().getName();
            } else {
                data[i][2] = order.getItems().get(0).getMenu().getName() + " 외 " + (order.getItems().size() - 1) + "건";
            }

            data[i][3] = String.format("%,d원", order.getTotalPrice());
            data[i][4] = order.getOrderTime().format(formatter);
        }

        JTable salesTable = new JTable(data, columnNames);
        salesTable.setRowHeight(25);
        salesTable.setEnabled(false); // 읽기 전용
        JScrollPane scrollPane = new JScrollPane(salesTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(tablePanel, BorderLayout.CENTER);

        // 하단 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // === CONTROLLER ===
        backButton.addActionListener(e -> {
            frame.dispose();
            openSellerWindow(seller);
        });

        frame.setVisible(true);
    }

    // [판매자] 5. 추천 메뉴 관리 화면
    public static void showRecommendMenuManagementScreen(User seller) {
        // === VIEW ===
        JFrame frame = new JFrame("추천 메뉴 관리");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // 상단 안내 패널
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        JLabel infoLabel = new JLabel("메뉴별 추천 분류를 설정하세요 (Best: 베스트 메뉴, New: 신메뉴)");
        infoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        infoPanel.add(infoLabel);
        frame.add(infoPanel, BorderLayout.NORTH);

        // 전체 메뉴 가져오기
        ArrayList<Menu> allMenus = menuList.getManageableMenus();

        // JTable 설정
        String[] columnNames = {"메뉴명", "가격", "카테고리", "추천 분류"};
        Object[][] data = new Object[allMenus.size()][4];

        for (int i = 0; i < allMenus.size(); i++) {
            Menu menu = allMenus.get(i);
            data[i][0] = menu.getName();
            data[i][1] = menu.getPrice() + "원";
            data[i][2] = menu.getOption().name();

            // 현재 추천 상태
            String currentRecommend = menu.getRecommend();
            if (currentRecommend == null || currentRecommend.isEmpty()) {
                data[i][3] = "없음";
            } else {
                data[i][3] = currentRecommend;
            }
        }

        JTable menuTable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // 추천 분류 열만 편집 가능
            }
        };

        menuTable.setRowHeight(30);

        // 추천 분류 열에 JComboBox 설정
        String[] recommendOptions = {"없음", "Best", "New"};
        JComboBox<String> comboBox = new JComboBox<>(recommendOptions);
        menuTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboBox));

        JScrollPane scrollPane = new JScrollPane(menuTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton applyButton = new JButton("적용");
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(applyButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // === CONTROLLER ===
        applyButton.addActionListener(e -> {
            try {
                // 테이블의 변경사항을 Menu 객체에 반영
                for (int i = 0; i < allMenus.size(); i++) {
                    Menu menu = allMenus.get(i);
                    String newRecommend = (String) menuTable.getValueAt(i, 3);

                    // "없음" 이면 null로 설정
                    if ("없음".equals(newRecommend)) {
                        menu.setRecommend(null);
                    } else {
                        menu.setRecommend(newRecommend);
                    }
                }

                // 파일 저장
                menuList.saveFile();

                JOptionPane.showMessageDialog(frame, "추천 메뉴가 성공적으로 적용되었습니다.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame,
                        "저장 중 오류가 발생했습니다: " + ex.getMessage(),
                        "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 뒤로가기 버튼
        backButton.addActionListener(e -> {
            frame.dispose();
            openSellerWindow(seller);
        });

        frame.setVisible(true);
    }


    /**
     * 구매자
     */
    // [구매자] 로그인 창
    public static void showCustomerLogin() {
        // =======  VIEW =======
        JFrame loginFrame = new JFrame();

        // 1. 입력 필드 영역
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel idLabel = new JLabel("아이디 : ");
        JTextField idField = new JTextField(15);

        JLabel pwLabel = new JLabel("비밀번호 : ");
        JPasswordField pwField = new JPasswordField(15);

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(pwLabel);
        inputPanel.add(pwField);

        // 2. 버튼 영역
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton loginButton = new JButton("로그인");
        JButton registerButton = new JButton("회원가입");
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        // 3. 프레임 구성
        loginFrame.setLayout(new BorderLayout(10, 10));
        loginFrame.add(inputPanel, BorderLayout.CENTER);
        loginFrame.add(buttonPanel, BorderLayout.SOUTH);

        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);


        // ======= CONTROLLER =======
        // 로그인 버튼 동작
        loginButton.addActionListener(e -> {
            // 입력값 가져오기
            String id = idField.getText();
            String password = new String(pwField.getPassword());

            // 유효성 검사
            if (id.trim().isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame,
                        "아이디와 비밀번호를 모두 입력해주세요.",
                        "입력 오류",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. 로그인 시도 (Model 호출)
            User loggedInCustomer = userList.customerLogin(id, password);

            // 4. 결과 처리
            if (loggedInCustomer != null) {
                // 로그인 성공
                JOptionPane.showMessageDialog(loginFrame,
                        "환영합니다!",
                        "로그인 성공",
                        JOptionPane.INFORMATION_MESSAGE);
                loginFrame.dispose();
                openCustomerWindow(loggedInCustomer); // 구매자 메뉴로 이동
            } else {
                // 로그인 실패
                JOptionPane.showMessageDialog(loginFrame,
                        "로그인 실패! 아이디 또는 비밀번호를 확인해주세요.",
                        "로그인 실패",
                        JOptionPane.ERROR_MESSAGE);
                pwField.setText(""); // 비밀번호 필드 초기화
                idField.requestFocus(); // 아이디 필드에 포커스
            }
        });

        // 회원가입 버튼 동작
        registerButton.addActionListener(e -> {
            loginFrame.dispose();
            showCustomerSignup();

        });

        // 뒤로가기 버튼 동작
        backButton.addActionListener(e -> {
            loginFrame.dispose();
            showMainScreen();
        });

        loginFrame.setVisible(true);
    }

    // [구매자] 회원가입 창
    public static void showCustomerSignup() {
        // ======= VIEW =======
        JFrame signupFrame = new JFrame("구매자 회원가입");

        // input 영역
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel idLabel = new JLabel("아이디 :");
        JTextField idField = new JTextField(15);

        JLabel pwLabel = new JLabel("비밀번호 : ");
        JPasswordField pwField = new JPasswordField(15);

        JLabel pwCheckLabel = new JLabel("비밀번호 확인 : ");
        JPasswordField pwCheckField = new JPasswordField(15);

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(pwLabel);
        inputPanel.add(pwField);
        inputPanel.add(pwCheckLabel);
        inputPanel.add(pwCheckField);

        // button 영역
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton signupButton = new JButton("회원가입");
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(signupButton);
        buttonPanel.add(backButton);

        signupFrame.setLayout(new BorderLayout(10, 10));
        signupFrame.add(inputPanel, BorderLayout.CENTER);
        signupFrame.add(buttonPanel, BorderLayout.SOUTH);

        signupFrame.setSize(400, 250);
        signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // ======= CONTROLLER =======
        // 회원가입 동작
        signupButton.addActionListener(e -> {
            // 입력 받기
            String id = idField.getText();
            String password = new String(pwField.getPassword());
            String passwordCheck = new String(pwCheckField.getPassword());

            // 입력 유효성 검사
            // 입력란이 비어있을 때
            if (id.trim().isEmpty() || password.isEmpty() || passwordCheck.isEmpty()) {
                JOptionPane.showMessageDialog(signupFrame,
                        "아이디, 비밀번호, 비밀번호 확인을 모두 입력해주세요.",
                        "입력 오류",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // pw, pwCheck가 동일하지 않을 때
            if (!password.equals(passwordCheck)) {
                JOptionPane.showMessageDialog(signupFrame,
                        "비밀번호가 일치하지 않습니다.",
                        "입력 오류",
                        JOptionPane.ERROR_MESSAGE);
                pwField.setText("");
                pwCheckField.setText("");
                pwField.requestFocus();
                return;
            }

            // 유효하다면 회원가입 시도 (Model 호출)
            try {
                boolean success = userList.registerCustomer(id, password);

                if (success) {
                    JOptionPane.showMessageDialog(signupFrame, "회원가입 성공! 로그인 화면으로 돌아갑니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                    signupFrame.dispose();
                    showCustomerLogin(); // 로그인 화면으로 이동
                } else {
                    JOptionPane.showMessageDialog(signupFrame, "이미 존재하는 아이디입니다. 다시 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    idField.setText("");
                    idField.requestFocus();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(signupButton, "파일 저장 중 오류가 발생했습니다", "오류", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        // 뒤로가기 동작
        backButton.addActionListener(e -> {
            signupFrame.dispose();
            showCustomerLogin(); // 구매자 로그인 화면으로 돌아가기
        });

        signupFrame.setVisible(true);
    }

    // [구매자] 메뉴 창
    public static void openCustomerWindow(User loggedInCustomer) {
        // === VIEW ===
        JFrame customerFrame = new JFrame("카페 주문 서비스 - 고객");
        customerFrame.setSize(500, 500);
        customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setLayout(new BorderLayout(10, 10));

        // 상단 환영 패널
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        JLabel welcomeLabel = new JLabel("환영합니다, " + loggedInCustomer.getId() + "님!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        welcomePanel.add(welcomeLabel);
        customerFrame.add(welcomePanel, BorderLayout.NORTH);

        // 중앙 메뉴 버튼 패널
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 버튼 생성
        JButton orderMenuButton = new JButton("메뉴 보기 및 주문");
        JButton orderHistoryButton = new JButton("주문 내역 확인");
        JButton recommendMenuButton = new JButton("오늘의 추천 메뉴");
        JButton favoriteMenuButton = new JButton("나만의 메뉴 (찜)");
        JButton logoutButton = new JButton("로그아웃");

        // 버튼 폰트 설정
        Font buttonFont = new Font("맑은 고딕", Font.PLAIN, 16);
        orderMenuButton.setFont(buttonFont);
        orderHistoryButton.setFont(buttonFont);
        recommendMenuButton.setFont(buttonFont);
        favoriteMenuButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

        // 버튼 크기 설정
        Dimension buttonSize = new Dimension(400, 60);
        orderMenuButton.setPreferredSize(buttonSize);
        orderHistoryButton.setPreferredSize(buttonSize);
        recommendMenuButton.setPreferredSize(buttonSize);
        favoriteMenuButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);

        // 버튼 패널에 추가
        menuPanel.add(orderMenuButton);
        menuPanel.add(orderHistoryButton);
        menuPanel.add(recommendMenuButton);
        menuPanel.add(favoriteMenuButton);
        menuPanel.add(logoutButton);

        customerFrame.add(menuPanel);

        // === CONTROLLER ===

        // 1. 메뉴 보기 및 주문
        orderMenuButton.addActionListener(e -> {
            customerFrame.dispose();
            showOrderMenuScreen(loggedInCustomer);
        });

        // 2. 주문 내역 확인
        orderHistoryButton.addActionListener(e -> {
            customerFrame.dispose();
        });

        // 3. 오늘의 추천 메뉴
        recommendMenuButton.addActionListener(e -> {
            customerFrame.dispose();
            showRecommendMenuViewScreen(loggedInCustomer);
        });

        // 4. 나만의 메뉴 (찜)
        favoriteMenuButton.addActionListener(e -> {
            customerFrame.dispose();
        });


        // 5. 로그아웃
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    customerFrame,
                    "로그아웃 하시겠습니까?",
                    "로그아웃",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                customerFrame.dispose();
                showMainScreen();
            }
        });

        customerFrame.setVisible(true);
    }

    // [구매자] 1. 메뉴 보기 및 주문 화면
    public static void showOrderMenuScreen(User customer) {
        // todo : 지점 선택 기능 (현재는 하드코딩)
        int storeId = 1;
        String storeName = storeList.findStoreById(storeId).getStoreName();

        // === VIEW ===
        JFrame frame = new JFrame("메뉴 보기 및 주문 - " + storeName);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // 주문 가능한 메뉴 가져오기
        ArrayList<Menu> orderableMenus = menuList.showAndGetOrderableMenus(storeId);

        // 메뉴가 없을 때
        if (orderableMenus.isEmpty()) {
            JPanel emptyPanel = new JPanel(new GridLayout(2, 1));
            emptyPanel.add(new JLabel("현재 주문 가능한 메뉴가 없습니다.", JLabel.CENTER));

            JButton backButton = new JButton("뒤로가기");
            backButton.addActionListener(e -> {
                frame.dispose();
                openCustomerWindow(customer);
            });

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(backButton);
            emptyPanel.add(buttonPanel);

            frame.add(emptyPanel, BorderLayout.CENTER);
            frame.setVisible(true);
            return;
        }

        // 장바구니 (전역 변수처럼 사용하기 위해 final ArrayList  사용)
        final ArrayList<OrderItem> cart = new ArrayList<>();

        // 상단 정보 패널
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        JLabel cartLabel = new JLabel("장바구니: 0개");
        cartLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        infoPanel.add(cartLabel);
        frame.add(infoPanel, BorderLayout.NORTH);

        // 중앙 메뉴 테이블
        String[] columnNames = {"메뉴명", "가격", "카테고리", "재고 상태"};
        Object[][] data = new Object[orderableMenus.size()][4];

        for (int i = 0; i < orderableMenus.size(); i++) {
            Menu menu = orderableMenus.get(i);
            data[i][0] = menu.getName();
            data[i][1] = menu.getPrice();
            data[i][2] = menu.getOption();

            // 재고 상태 확인
            MenuStatus menuStatus = menuStatusList.findMenuStatus(storeId, menu.getId());
            if (menuStatus != null) {
                data[i][3] = menuStatus.getStatus().getDisplayStatus();
            } else {
                data[i][3] = "알 수 없음";
            }
        }

        JTable menuTable = new JTable(data, columnNames);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 하단 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addToCartButton = new JButton("장바구니 담기");
        JButton viewCartButton = new JButton("장바구니 보기");
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(addToCartButton);
        buttonPanel.add(viewCartButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // === CONTROLLER ===

        // 장바구니 담기 버튼
        addToCartButton.addActionListener(e -> {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "메뉴를 선택해주세요.", "알림", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Menu selectedMenu = orderableMenus.get(selectedRow);

            // 재고 확인
            if (!menuStatusList.isAvailable(storeId, selectedMenu.getId())) {
                JOptionPane.showMessageDialog(frame,
                        "죄송합니다. '" + selectedMenu.getName() + "' 메뉴는 현재 품절입니다.",
                        "품절", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 옵션 선택 다이얼로그 열기
            OrderItem newItem = showOrderItemOptionsDialog(frame, selectedMenu);

            if (newItem != null) {
                cart.add(newItem);
                cartLabel.setText("장바구니: " + cart.size() + "개");
                JOptionPane.showMessageDialog(frame,
                        "'" + selectedMenu.getName() + "' 메뉴가 장바구니에 담겼습니다.");
            }
        });

        // 장바구니 보기 버튼
        viewCartButton.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "장바구니가 비어있습니다.",
                        "알림", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            frame.dispose();
//            showCartScreen(customer, cart, storeId);
        });

        // 뒤로가기 버튼
        backButton.addActionListener(e -> {
            if (!cart.isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(frame,
                        "장바구니에 담긴 메뉴가 있습니다. 정말 나가시겠습니까?",
                        "확인", JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            frame.dispose();
            openCustomerWindow(customer);
        });

        frame.setVisible(true);
    }

    // [구매자] 1-1. 옵션 선택 다이얼로그
    public static OrderItem showOrderItemOptionsDialog(JFrame parentFrame, Menu menu) {
        // ======= VIEW =======
        JDialog dialog = new JDialog(parentFrame, "옵션 선택 - " + menu.getName(), true);
        dialog.setSize(400, 550);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setLocationRelativeTo(parentFrame);

        // 최종 선택값 저장용
        final String[] finalOptions = {null}; // 추가 옵션
        final int[] finalPrice = {menu.getPrice()};
        final String[] finalTemp = {"ICE"}; // 기본값
        final String[] finalCup = {"일회용컵"}; // 기본값

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // === 4. 최종 가격 표시 (먼저 선언해야 다른 곳에서 참조 가능) ===
        JLabel priceLabel = new JLabel("최종 가격: " + finalPrice[0] + "원");
        priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        // === 1. 추가 옵션 (카테고리별) ===
        if (menu.getOption() == MenuCategory.COFFEE) {
            JLabel optionLabel = new JLabel("샷 옵션:");
            optionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            mainPanel.add(optionLabel);
            mainPanel.add(Box.createVerticalStrut(5));

            String[] options = {"기본(2샷)", "연하게(1샷)", "샷추가(+500원)", "디카페인(+1000원)"};
            JComboBox<String> optionCombo = new JComboBox<>(options);
            mainPanel.add(optionCombo);
            mainPanel.add(Box.createVerticalStrut(15));

            optionCombo.addActionListener(e -> {
                int selected = optionCombo.getSelectedIndex();
                finalPrice[0] = menu.getPrice(); // 초기화

                if (selected == 0) {
                    finalOptions[0] = "기본(2샷)";
                } else if (selected == 1) {
                    finalOptions[0] = "연하게(1샷)";
                } else if (selected == 2) {
                    finalOptions[0] = "샷추가(3샷)";
                    finalPrice[0] += 500;
                } else if (selected == 3) {
                    finalOptions[0] = "디카페인";
                    finalPrice[0] += 1000;
                }

                priceLabel.setText("최종 가격: " + finalPrice[0] + "원");
            });

            // 기본값 설정
            finalOptions[0] = "기본(2샷)";
        } else if (menu.getOption() == MenuCategory.LATTE) {
            JLabel optionLabel = new JLabel("우유 옵션:");
            optionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            mainPanel.add(optionLabel);
            mainPanel.add(Box.createVerticalStrut(15));

            String[] options = {"일반 우유", "오트(귀리) (+1000원)"};
            JComboBox<String> optionCombo = new JComboBox<>(options);
            mainPanel.add(optionCombo);
            mainPanel.add(Box.createVerticalStrut(15));

            optionCombo.addActionListener(e -> {
                int selected = optionCombo.getSelectedIndex();
                finalPrice[0] = menu.getPrice();

                if (selected == 0) {
                    finalOptions[0] = "일반 우유";
                } else if (selected == 1) {
                    finalOptions[0] = "오트(귀리)";
                    finalPrice[0] += 1000;
                }

                priceLabel.setText("최종 가격: " + finalPrice[0] + "원");
            });

            finalOptions[0] = "일반 우유";
        } else if (menu.getOption() == MenuCategory.TEA) {
            JLabel optionLabel = new JLabel("물 양:");
            optionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            mainPanel.add(optionLabel);
            mainPanel.add(Box.createVerticalStrut(5));

            String[] options = {"보통", "적게"};
            JComboBox<String> optionCombo = new JComboBox<>(options);
            mainPanel.add(optionCombo);
            mainPanel.add(Box.createVerticalStrut(15));

            optionCombo.addActionListener(e -> {
                int selected = optionCombo.getSelectedIndex();
                if (selected == 0) {
                    finalOptions[0] = "물 양 보통";
                } else {
                    finalOptions[0] = "물 양 적게";
                }
            });

            finalOptions[0] = "물 양 보통";
        } else {
            finalOptions[0] = "선택안함";
        }

        // === 2. 온도 선택 (COFFEE, TEA만)
        if (menu.getOption() == MenuCategory.COFFEE || menu.getOption() == MenuCategory.TEA) {
            JLabel tempLabel = new JLabel("온도:");
            tempLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            mainPanel.add(tempLabel);
            mainPanel.add(Box.createVerticalStrut(5));

            String[] tempOptions = {"ICE", "HOT"};
            JComboBox<String> tempCombo = new JComboBox<>(tempOptions);
            mainPanel.add(tempCombo);
            mainPanel.add(Box.createVerticalStrut(15));

            tempCombo.addActionListener(e -> {
                finalTemp[0] = (String) tempCombo.getSelectedItem();
            });
        }

        // === 3. 컵 선택 ===
        JLabel cupLabel = new JLabel("컵:");
        cupLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        mainPanel.add(cupLabel);
        mainPanel.add(Box.createVerticalStrut(5));

        String[] cupOptions = {"일회용컵", "매장컵", "개인컵 (-300원)"};
        JComboBox<String> cupCombo = new JComboBox<>(cupOptions);
        mainPanel.add(cupCombo);
        mainPanel.add(Box.createVerticalStrut(15));

        cupCombo.addActionListener(e -> {
            int selected = cupCombo.getSelectedIndex();

            // 먼저 기본 가격으로 쵝화 (중복 할인 방지)
            finalPrice[0] = menu.getPrice();

            // 추가 옵션 가격 다시 적용
            if (menu.getOption() == MenuCategory.COFFEE) {
                if (finalOptions[0].contains("샷추가")) {
                    finalPrice[0] += 500;
                } else if (finalOptions[0].contains("디카페인")) {
                    finalPrice[0] += 1000;
                }
            } else if (menu.getOption() == MenuCategory.LATTE) {
                if (finalOptions[0].contains("오트")) {
                    finalPrice[0] += 1000;
                }
            }

            if (selected == 0) {
                finalCup[0] = "일회용컵";
            } else if (selected == 1) {
                finalCup[0] = "매장컵";
            } else if (selected == 2) {
                finalCup[0] = "개인컵";
                finalPrice[0] -= 300;
            }

            priceLabel.setText("최종 가격: " + finalPrice[0] + "원");
        });

        // === 4. 최종 가격 표시 ===
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(priceLabel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton confirmButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // ======= CONTROLLER =======

        // 결과 저장용
        final OrderItem[] result = {null};

        // 확인 버튼
        confirmButton.addActionListener(e -> {
            result[0] = new OrderItem(menu, finalPrice[0], finalOptions[0], finalCup[0], finalTemp[0]);
            dialog.dispose();
        });

        // 취소 버튼
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);
        return result[0];
    }

    // [구매자] 1-2. 장바구니 보기 화면

    // [구매자] 3. 오늘의 추천 메뉴 화면
    public static void showRecommendMenuViewScreen(User customer) {
        // === VIEW ===
        JFrame frame = new JFrame("오늘의 추천 메뉴");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // 상단 타이틀
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        JLabel titleLabel = new JLabel("오늘의 추천 메뉴", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        // 중앙 추천 메뉴 패널
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // 전체 메뉴 가져오기
        ArrayList<Menu> allMenus = menuList.getManageableMenus();

        // Best 메뉴 수집
        ArrayList<Menu> bestMenus = new ArrayList<>();
        ArrayList<Menu> newMenus = new ArrayList<>();

        for (Menu menu : allMenus) {
            String recommend = menu.getRecommend();
            if ("Best".equals(recommend)) {
                bestMenus.add(menu);
            } else if ("New".equals(recommend)) {
                newMenus.add(menu);
            }
        }

        // Best 메뉴 표시
        if (!bestMenus.isEmpty()) {
            JLabel bestLabel = new JLabel("Best Menu");
            bestLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            contentPanel.add(bestLabel);
            contentPanel.add(Box.createVerticalStrut(10));

            for (Menu menu : bestMenus) {
                JLabel menuLabel = new JLabel("  • " + menu.getName() + " - " + menu.getPrice() + "원");
                menuLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
                contentPanel.add(menuLabel);
                contentPanel.add(Box.createVerticalStrut(5));
            }
            contentPanel.add(Box.createVerticalStrut(20));
        }

        // New 메뉴 표시
        if (!newMenus.isEmpty()) {
            JLabel newLabel = new JLabel("New Menu");
            newLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            contentPanel.add(newLabel);
            contentPanel.add(Box.createVerticalStrut(10));

            for (Menu menu : newMenus) {
                JLabel menuLabel = new JLabel("  • " + menu.getName() + " - " + menu.getPrice() + "원");
                menuLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
                contentPanel.add(menuLabel);
                contentPanel.add(Box.createVerticalStrut(5));
            }
        }

        // 추천 메뉴가 없을 때
        if (bestMenus.isEmpty() && newMenus.isEmpty()) {
            JLabel noMenuLabel = new JLabel("현재 추천 메뉴가 없습니다.", JLabel.CENTER);
            noMenuLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            contentPanel.add(noMenuLabel);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 하단 버튼
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("뒤로가기");

        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // === CONTROLLER ===
        backButton.addActionListener(e -> {
            frame.dispose();
            openCustomerWindow(customer);
        });

        frame.setVisible(true);
    }


}

