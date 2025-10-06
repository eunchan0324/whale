import menu.*;
import order.Order;
import order.OrderItem;
import order.OrderList;
import order.OrderStatus;
import store.StoreList;
import user.User;
import user.UserList;
import user.UserRole;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        // 변수
//        int 수용량 = 5; // 배열의 전체 크기
//        String[] menuName = new String[수용량];
//        int[] menuPrice = new int[수용량];
//        String[] menuOption = new String[수용량];
//        int 칸 = 0; // 현재 차있는 배열의 크기


        // 배열
//        ArrayList<Integer> menuPrice = new ArrayList<>();
//        ArrayList<String> menuOption = new ArrayList<>();


        // 클래스
        // + 메뉴를 담는 바구니 (메뉴 리스트) 필요
        // 메뉴 리스트라는 클래스 만들기
        // 메뉴 리스트 안에 같은 자료형 (배열) 이 들어감
        // 클래스 이름은 : 메뉴 / 메뉴 리스트로 정하기 (영어)

        // 객체 생성 == Menu의 인스턴스 menu 생성
//        menu.Menu menu = new menu.Menu();
        // 객체 생성 == MenuList의 인스턴스 menuList 생성

        StoreList storeList = new StoreList();
        UserList userList = new UserList(storeList);
        MenuStatusList menuStatusList = new MenuStatusList();
        MenuList menuList = new MenuList(menuStatusList, storeList);
        OrderList orderList = new OrderList(menuList, storeList);
        MyMenu myMenu = new MyMenu();

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
                                menuList.menuListCheck();
                            }

                            // 1-3. 메뉴 수정
                            else if (choice == 3) {
                                // todo : 메서드에 내용 합치기
                                System.out.println("[메뉴 수정]");
                                System.out.println("어떤 메뉴를 수정할까요?");
                                menuList.menuListCheck();

                                System.out.print("수정하고 싶은 메뉴의 메뉴명을 정확하게 입력해주세요 : ");
                                String 수정할메뉴 = sc.nextLine();
                                menuList.menuEdit(수정할메뉴);
                            }

                            // 1-4. 메뉴 삭제
                            // todo : 메서드에 내용 합치기
                            else if (choice == 4) {
                                System.out.println("[메뉴 목록]");
                                menuList.menuListCheck();

                                System.out.print("삭제할 메뉴명을 정확히 입력해주세요 : ");
                                String 삭제할메뉴 = sc.nextLine();
                                menuList.menuDelete(삭제할메뉴);
                                System.out.println();
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
                                    System.out.println("1. 주문 목록 변경");
                                    System.out.println("2. 주문 상태 변경");
                                    System.out.println("3. 뒤로가기");
                                    System.out.print(" : ");
                                    int orderMenuChoice = sc.nextInt();
                                    sc.nextLine();

                                    // 1-1. 주문 목록 변경
                                    if (orderMenuChoice == 1) {
                                        orderList.checkOrders();
                                    }

                                    // 1-2. 주문 상태 변경
                                    else if (orderMenuChoice == 2) {
                                        orderList.showPendingOrders();
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
                                            System.out.println("[재고 수량 변경]");
                                            menuList.showStockStatusForSeller(storeId);

                                            System.out.print("수정할 메뉴의 ID를 입력해주세요 : ");
                                            int menuId = sc.nextInt();
                                            sc.nextLine();

                                            System.out.print("수정할 재고의 수량을 입력해주세요 : ");
                                            int newStock = sc.nextInt();
                                            sc.nextLine();

                                            menuStatusList.updateStock(storeId, menuId, newStock);
                                        }

                                        // 3-3. 판매 상태 변경
                                        else if (choice == 3) {
                                            System.out.println();
                                            System.out.println("[판매 상태 변경]");
                                            menuList.showStockStatusForSeller(storeId);

                                            System.out.print("수정할 메뉴의 ID를 입력해주세요 : ");
                                            int menuId = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println("수정할 메뉴의 상태를 입력해주세요");
                                            System.out.println("1. 판매 가능 (AVAILABLE)");
                                            System.out.println("2. 판매 중지 (SOLD_OUT)");
                                            System.out.print(" : ");
                                            int statusChoice = sc.nextInt();
                                            sc.nextLine();

                                            MenuSaleStatus newstatus = null;

                                            if (statusChoice == 1) {
                                                newstatus = MenuSaleStatus.AVAILABLE;
                                            } else if (statusChoice == 2) {
                                                newstatus = MenuSaleStatus.SOLD_OUT;
                                            }

                                            if (newstatus != null) {
                                                menuStatusList.updateStatus(storeId, menuId, newstatus);
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
                                    menuList.showManageableMenuList(storeId);
                                    System.out.println();
                                    System.out.println("1. 판매 메뉴 등록하기");
                                    System.out.println("2. 판매 메뉴 삭제하기");
                                    System.out.println("3. 뒤로가기");
                                    System.out.print(" : ");
                                    int salesChoice = sc.nextInt();
                                    sc.nextLine();

                                    // 5-1. 판매 메뉴 등록하기
                                    if (salesChoice == 1) {
                                        System.out.print("등록할 메뉴의 ID를 입력해주세요 : ");
                                        int menuId = sc.nextInt();
                                        sc.nextLine();

                                        menuStatusList.registerMenuForSale(storeId, menuId);
                                    }

                                    // 5-2. 판매 메뉴 삭제하기
                                    else if (salesChoice == 2) {
                                        System.out.print("삭제할 메뉴의 ID를 입력해주세요 : ");
                                        int menuId = sc.nextInt();
                                        sc.nextLine();

                                        menuStatusList.removeMenuForSale(storeId, menuId);
                                    }

                                    // 5-3. 뒤로가기
                                    else if (salesChoice == 3) {
                                        continue;
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
                                System.out.println("1. 메뉴 선택");
                                System.out.println("2. 주문 내역 확인 ");
                                System.out.println("3. 오늘의 메뉴 확인");
                                System.out.println("4. 나만의 메뉴 (찜)");
                                System.out.println("5. 로그아웃");
                                System.out.print(" : ");

                                int cusChoice = sc.nextInt();
                                sc.nextLine();

                                // 2-1. 메뉴 선택
                                if (cusChoice == 1) {
                                    // 1. 장바구니
                                    ArrayList<OrderItem> cart = new ArrayList<>();

                                    // 2. 메뉴 담기
                                    while (true) {
                                        // 메뉴 없을 때
                                        if (menuList.menuIsEmpty()) {
                                            System.out.println("등록된 메뉴가 없습니다. 메인 메뉴로 돌아갑니다.");
                                            break;
                                        }

                                        // 메뉴 있을 때
                                        System.out.println("[전체 메뉴]");
                                        menuList.menuListCheck(storeId);
                                        System.out.print("주문할 메뉴명을 정확히 입력해주세요 (주문 완료는 '완료' 입력) : ");
                                        String userInput = sc.nextLine();

                                        if (userInput.equals("완료")) {
                                            if (cart.isEmpty()) {
                                                System.out.println("장바구니가 비어있습니다. 주문을 종료합니다.");
                                            } else {
                                                System.out.println("주문을 시작합니다.");
                                            }
                                            break;
                                        }

                                        Menu targetMenu = menuList.findMenu(userInput);

                                        if (targetMenu != null) {
                                            // 재고 있을 때
                                            if (menuStatusList.isAvailable(storeId, targetMenu.getMenuId())) {
                                                OrderItem orderItem = new OrderItem(targetMenu);
                                                orderItem.tempSelect();
                                                orderItem.cupSelect();
                                                orderItem.optionSelect(userInput, menuList);

                                                cart.add(orderItem); // 장바구니에 추가
                                                System.out.println(targetMenu.getMenuName() + "(이)가 장바구니에 담겼습니다.");
                                            }
                                            // 재고 없을 때
                                            else {
                                                System.out.println("죄송합니다. 선택하신 메뉴는 현재 품절입니다..");
                                            }
                                        } else {
                                            System.out.println("메뉴명을 잘못 입력하셨습니다. 다시 시도해주세요.");
                                        }
                                        System.out.println("--- 현재 장바구니 : " + cart.size() + "개 ---");
                                        System.out.println();
                                    }

                                    // 3. 주문 처리 (루프 끝난 후)
                                    if (!cart.isEmpty()) {
                                        // 3-1. 총액 계산 및 order.Order 객체 생성
                                        int totalPirce = 0;
                                        for (OrderItem item : cart) {
                                            totalPirce += item.getFinalPrice(); //
                                        }

                                        Order finalOrder = new Order(loggedInCustomer.getId(), storeId, totalPirce, OrderStatus.ORDER_PLACED, cart);

                                        // 3-2. 주문 내역에 '최종 주문서 추가'
                                        orderList.addOrder(finalOrder);

                                        // 3-3. 재고 감소
                                        for (OrderItem item : cart) {
                                            menuStatusList.decreaseStock(storeId, item.getMenu().getMenuId());
                                        }

                                        System.out.println("주문이 완료되었습니다. 주문 번호 : " + finalOrder.getOrderId());

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
}

