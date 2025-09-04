import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

// menu 클래스 생성
class Menu {
    private String menuName;
    private int menuPrice;
    private String menuOption;

    public Menu() {
    }

    public Menu(String menuName, int menuPrice, String menuOption) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuOption = menuOption;
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

    public String getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(String menuOption) {
        this.menuOption = menuOption;
    }

    @Override
    public String toString() {
        return "[1. 메뉴 이름 = '" + menuName + '\'' + ", 2. 가격 =" + menuPrice + '원' + ", 3. 옵션 '" + menuOption + '\'' +
                ']';
    }
}

class MenuList {
    private ArrayList<Menu> menus = new ArrayList<>();

    // Create
    public void menuCreate(String name, int price, String option) {
        Menu menu = new Menu(name, price, option); // 메뉴 생성
        menus.add(menu);
    }

    // Read
    public void menuListCheck() {
        // 메뉴가 아무것도 등록되지 않았다면
        if (menus.isEmpty()) {
            System.out.println("등록된 메뉴가 없습니다. 메인 메뉴로 돌아갑니다.");
            System.out.println();
        }

        // 메뉴가 1개 이상 등록되어있다면
        else {
            System.out.println("[현재 등록된 메뉴 목록]");
            for (int i = 0; i < menus.size(); i++) {
                System.out.println(menus.get(i));
            }
            System.out.println();
        }

    }

    // Update
    public void menuEdit(String 수정할메뉴) {
        Scanner sc = new Scanner(System.in);
        boolean checker = false;

        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).getMenuName().equals(수정할메뉴)) {
                System.out.println("수정할 메뉴는 \"" + menus.get(i) + "\" 입니다.");
                System.out.println();

                System.out.print("수정할 항목의 번호를 입력해주세요 (1.메뉴명 2.가격 3.옵션) : ");
                int 수정할항목 = sc.nextInt();


                if (수정할항목 == 1) {
                    sc.nextLine();
                    System.out.print("수정할 메뉴명을 입력해주세요 : ");
                    String 수정할메뉴명 = sc.nextLine();

                    menus.get(i).setMenuName(수정할메뉴명);
                    System.out.println("메뉴명이 수정되었습니다.");
                    System.out.println();
                }

                if (수정할항목 == 2) {
                    sc.nextLine();
                    System.out.print("수정할 가격을 입력해주세요 (숫자만) : ");
                    int 수정할가격 = sc.nextInt();

                    menus.get(i).setMenuPrice(수정할가격);
                    System.out.println("메뉴 가격이 수정되었습니다.");
                    System.out.println();
                }

                if (수정할항목 == 3) {
                    sc.nextLine();
                    System.out.println("옵션은 다음과 같습니다");
                    System.out.println("  1. 커피류(coffee)\n" +
                            "  2. 라떼류(latte)\n" +
                            "  3. 차류(tea)");
                    System.out.print("수정할 옵션을 입력해주세요 (숫자만) : ");
                    String 수정할옵션 = sc.nextLine();

                    menus.get(i).setMenuOption(수정할옵션);
                    System.out.println("메뉴 옵션이 수정되었습니다.");
                    System.out.println();
                }
                checker = true;
                break;
            }
        }

        if (checker == false) {
            System.out.println("입력한 메뉴명이 정확하지 않습니다. 다시 입력해주세요.");
        }
    }


    // Delete
    public void menuDelete(String 삭제할메뉴) {
        boolean checker = false;

        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).getMenuName().equals(삭제할메뉴)) {
                menus.remove(i);
                System.out.println("선택한 메뉴가 삭제되었습니다.");
                checker = true;
                break;
            }
        }

        if (checker == false) {
            System.out.println("입력한 메뉴명이 정확하지 않습니다. 다시 입력해주세요.");
        }
    }

    // Menu Empty Check
    public boolean menuIsEmpty() {
        if (menus.isEmpty()) {
            return true;
        }
        return false;
    }


    // find Menu 객체 반환
    public Menu findMenu(String 주문할메뉴) {
        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).getMenuName().equals(주문할메뉴)) {
                return menus.get(i);
            }
        }
        return null;
    }
}

class OrderMenu {
    private Menu menu;
    private int finalPrice;
    private String finalOptions;
    private String finalCup;
    private String finalTemp;

    public Menu getMenu() {
        return menu;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public String getFinalOptions() {
        return finalOptions;
    }

    public String getFinalCup() {
        return finalCup;
    }

    public String getFinalTemp() {
        return finalTemp;
    }

    public void tempSelect() {
        System.out.print("온도를 선택해주세요 (HOT | ICED) : ");
        Scanner sc = new Scanner(System.in);
        String temp = sc.nextLine();

        this.finalTemp = temp;
    }

    public void cupSelect() {
        System.out.println("컵 사이즈를 입력해주세요");
        System.out.println("Tall (355ml)");
        System.out.println("Grande (473ml) + 500원");
        System.out.println("Venti (591ml) + 1000원");
        System.out.printf(" : ");

        Scanner sc = new Scanner((System.in));
        String cup = sc.nextLine();

        if (cup.equals("Grande")) {
            this.finalPrice += 500;
        } else if (cup.equals("Venti")) {
            this.finalPrice += 1000;
        }

        this.finalCup = cup;
    }


    public void optionSelect(String 주문할메뉴, MenuList menuList) {
        this.menu = menuList.findMenu(주문할메뉴);

        Scanner sc = new Scanner(System.in);

        if (menuList.findMenu(주문할메뉴).getMenuOption().equals("1")) {

            System.out.println("샷 옵션을 선택해주세요");
            System.out.println("1.기본(2샷) " + System.lineSeparator() +
                    "2.연하게(1샷)" + System.lineSeparator() +
                    "3.샷추가(3샷) + 500원" + System.lineSeparator() +
                    "4.디카페인 + 1000원");
            System.out.print(" : ");

            int choiceOption = sc.nextInt();
            sc.nextLine();

            if (choiceOption == 1) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice();
                this.finalOptions = "기본(2샷)";
            } else if (choiceOption == 2) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice();
                this.finalOptions = "연하게(1샷)";
            } else if (choiceOption == 3) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice() + 500;
                this.finalOptions = "샷추가(3샷)";
            } else if (choiceOption == 4) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice() + 1000;
                this.finalOptions = "디카페인";
            }
        } else if (menuList.findMenu(주문할메뉴).getMenuOption().equals("2")) {
            System.out.println("우유 옵션을 선택해주세요");
            System.out.println("1. 일반 우유");
            System.out.println("2. 오트(귀리) + 1000원");
            System.out.print(" : ");

            int choiceOption = sc.nextInt();
            sc.nextLine();

            if (choiceOption == 1) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice();
                this.finalOptions = "일반 우유";
            } else if (choiceOption == 2) {
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice() + 1000;
                this.finalOptions = "오트(귀리)";
            }
        } else if (menuList.findMenu((주문할메뉴)).getMenuOption().equals("3")) {
            System.out.println("물 양은 선택해주세요");
            System.out.println("1. 보통");
            System.out.println("2. 적게");
            System.out.print(" : ");

            int choiceOption = sc.nextInt();
            sc.nextLine();

            if (choiceOption == 1) {
                this.finalOptions = "물 양 보통";
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice();
            } else if (choiceOption == 2) {
                this.finalOptions = "물 양 적게";
                this.finalPrice += menuList.findMenu(주문할메뉴).getMenuPrice();

            }

        }
        System.out.println();
        System.out.println("[최종 주문 내역 확인]");
        System.out.println("메뉴명 : " + menu.getMenuName());
        System.out.println("온도 : " + finalTemp);
        System.out.println("사이즈 : " + finalCup);
        System.out.println("옵션 : " + finalOptions);
        System.out.println("최종 가격 : " + finalPrice + "원");
        System.out.println("-------주문이 완료되었습니다. 잠시만 기다려주세요.------");
        System.out.println();
    }
}

class OrderHistory {
    ArrayList<OrderMenu> orderHistory = new ArrayList<>();

    public void OrderHistoryAdd(OrderMenu orderMenu) {
        this.orderHistory.add(orderMenu);
    }

    public void OrderCheck() {
        if (orderHistory.isEmpty()) {
            System.out.println("현재 주문 내역이 존재하지 않습니다.");
            System.out.println();
        } else {
            System.out.println("[현재 주문 내역]");
            for (int i = 0; i < orderHistory.size(); i++) {
                System.out.println("-----주문 No." + (i+1) + "-----");
                System.out.println("메뉴 : " + orderHistory.get(i).getMenu().getMenuName());
                System.out.println("온도 : " + orderHistory.get(i).getFinalTemp());
                System.out.println("컵 : " + orderHistory.get(i).getFinalCup());
                System.out.println("옵션 : " + orderHistory.get(i).getFinalOptions());
                System.out.println("가격 : " + orderHistory.get(i).getFinalPrice() + "원");
                System.out.println();
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
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
//        Menu menu = new Menu();
        // 객체 생성 == MenuList의 인스턴스 menuList 생성
        MenuList menuList = new MenuList();
        OrderHistory orderHistory = new OrderHistory();


//         서비스 시작
        while (true) {
            System.out.println();
            System.out.println("안녕하세요, 카페 주문 서비스입니다. 역할을 선택해주세요.");
            System.out.println("1. 사장");
            System.out.println("2. 손님");
            System.out.println("3. 프로그램 종료");
            System.out.print("역할을 선택해주세요 : ");

            // 역할 선택
            Scanner sc = new Scanner(System.in);
            int role = sc.nextInt();
            sc.nextLine();

            System.out.println();

            // 1. 주인일 때
            if (role == 1) {
                while (true) {
                    System.out.println("안녕하세요 사장님, 카페 주문 서비스입니다.");
                    System.out.println("1. 메뉴 등록 및 관리");
                    System.out.println("2. 주문 내역 확인");
                    System.out.println("3. 추천 메뉴 등록 및 관리");
                    System.out.println("4. 이벤트 등록 및 관리");
                    System.out.println("5. 쿠폰 등록 및 관리");
                    System.out.println("6. 역할 선택으로 돌아가기");
                    System.out.print("할 일을 선택해주세요 : ");

                    int menuSelect = sc.nextInt();
                    sc.nextLine();

                    if (menuSelect == 1) {
                        System.out.println();
                        System.out.println("[메뉴 등록 및 관리]");
                        System.out.println("1. 메뉴 등록");
                        System.out.println("2. 등록 메뉴 확인");
                        System.out.println("3. 메뉴 수정");
                        System.out.println("4. 메뉴 삭제");
                        System.out.print("할 일을 선택해주세요 : ");

                        int choice = sc.nextInt();
                        sc.nextLine();
                        System.out.println();


                        // 1-1 메뉴 등록 (Create)
                        if (choice == 1) {
                            System.out.print("메뉴 이름을 작성해주세요 : ");
                            String menuName = sc.nextLine();

                            System.out.print("메뉴의 가격을 입력해주세요 : ");
                            int menuPrice = sc.nextInt();
                            sc.nextLine();

                            System.out.println("메뉴의 종류를 선택해주세요");
                            System.out.println("  1. 커피류(coffee)");
                            System.out.println("  2. 라떼류(latte)");
                            System.out.println("  3. 차류(tea)");
                            System.out.print(" : ");
                            String menuOption = sc.nextLine();

                            // Menu 클래스 생성자
                            menuList.menuCreate(menuName, menuPrice, menuOption);
                            System.out.println("등록이 완료되었습니다.");
                            System.out.println();
                        }

                        // 1-2. 등록 메뉴 확인 (Read)
                        if (choice == 2) {
                            menuList.menuListCheck();
                        }

                        // 1-3. 메뉴 수정
                        if (choice == 3) {
                            System.out.println("[메뉴 수정]");
                            System.out.println("어떤 메뉴를 수정할까요?");
                            menuList.menuListCheck();

                            System.out.print("수정하고 싶은 메뉴의 메뉴명을 정확하게 입력해주세요 : ");
                            String 수정할메뉴 = sc.nextLine();
                            menuList.menuEdit(수정할메뉴);
                        }

                        // 1-4. 메뉴 삭제
                        if (choice == 4) {
                            System.out.println("[메뉴 목록]");
                            menuList.menuListCheck();

                            System.out.print("삭제할 메뉴명을 정확히 입력해주세요 : ");
                            String 삭제할메뉴 = sc.nextLine();
                            menuList.menuDelete(삭제할메뉴);
                            System.out.println();
                        }


                    } else if (menuSelect == 2) {
                        orderHistory.OrderCheck();
                    }

                    // 1-6. 역할 선택으로 돌아가기
                    if (menuSelect == 6) {
                        System.out.println("역할 선택으로 돌아갑니다.");
                        break;
                    }


                }
            }


            // 2. 손님일 때
            if (role == 2) {
                while (true) {
                    System.out.println("안녕하세요 손님, 카페 주문 서비스입니다.");
                    System.out.println("할 일을 선택해주세요.");
                    System.out.println("1. 메뉴 선택");
                    System.out.println("2. 주문 내역 확인 ");
                    System.out.println("3. 오늘의 메뉴 추천");
                    System.out.println("4. 찜한 메뉴");
                    System.out.println("5. 역할 선택으로 돌아가기");
                    System.out.print(" : ");

                    int cusChoice = sc.nextInt();
                    sc.nextLine();

                    // 2-1. 메뉴 선택
                    if (cusChoice == 1) {
                        // 메뉴 안내
                        System.out.println("[전체 메뉴]");
                        if (menuList.menuIsEmpty()) {
                            System.out.println("등록된 메뉴가 없습니다. 메인 메뉴로 돌아갑니다.");
                            break;
                        } else {
                            menuList.menuListCheck();
                            System.out.printf("주문할 메뉴명을 정확히 입력해주세요 : ");
                            String 주문할메뉴 = sc.nextLine();

                            OrderMenu orderMenu = new OrderMenu();
                            orderMenu.tempSelect();
                            orderMenu.cupSelect();
                            orderMenu.optionSelect(주문할메뉴, menuList);
                            orderHistory.OrderHistoryAdd(orderMenu);

                        }
                    }

                    if (cusChoice == 2) {
                        orderHistory.OrderCheck();
                    }

                    if (cusChoice == 5) {
                        System.out.println("역할 선택으로 돌아갑니다.");
                        System.out.println();
                        break;
                    }
                }
            }


            // 3. 프로그램 종료
            if (role == 3) {
                break;
            }


        }
    }
}

