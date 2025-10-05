package menu;

import constant.Constants;
import store.StoreList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

// todo : 1. 각 기능별 role에 따라 인가 가능한 것 체크하기 + 일단 User에 있는 맴버 변수를 가져와서 비교해보기
// todo : 2. 인가 기능을 따로 뺄 수 있는지 확인해보기
public class MenuList {
    private final ArrayList<Menu> menus = new ArrayList<>();
    private MenuStatusList menuStatusList;
    private StoreList storeList;

    public MenuList(MenuStatusList menuStatusList, StoreList storeList) throws IOException {
        this.menuStatusList = menuStatusList;
        this.storeList = storeList;
        loadMenuFile();
    }

    // menu.Menu Create
    public void menuCreate() throws IOException {
        Scanner sc = new Scanner(System.in);

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
        int menuOptionNum = sc.nextInt();
        sc.nextLine();

        MenuCategory menuOption = MenuCategory.fromValue(menuOptionNum);
        if (menuOption == null) {
            System.out.println("잘못된 번호입니다.");
            return;
        }
        Menu menu = new Menu(menuName, menuPrice, menuOption);
        menus.add(menu);

        Path menuFilePath = Constants.BASE_PATH.resolve("Menus.txt");
        FileWriter writer = new FileWriter(menuFilePath.toFile(), true);
        writer.write(menu.getMenuId() + "," + menu.getMenuName() + "," + menu.getMenuPrice() + "," + menu.getMenuOption() + "\n");
        writer.close();

        System.out.println("등록이 완료되었습니다.");
        System.out.println();
    }

    // menu.Menu Read - 관리자용
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

    // menu.Menu Read - 구매자용
    public void menuListCheck(int storeId) {
        // 메뉴가 아무것도 등록되지 않았다면
        if (menus.isEmpty()) {
            System.out.println("등록된 메뉴가 없습니다. 메인 메뉴로 돌아갑니다.");
            return;
        }

        // 메뉴가 1개 이상 등록되어있다면
        System.out.println("\n--- 주문 가능한 메뉴 목록 ---");
        boolean hasOrderableMenu = false;
        for (Menu menu : menus) {
            if (menuStatusList.isAvailable(storeId, menu.getMenuId())) {
                String menuInfo = menu.getMenuId() + ". " + menu.getMenuName() + " | " + menu.getMenuPrice() + "원";
                System.out.println(menuInfo);
                hasOrderableMenu = true;
            }
        }

        // 주문 가능한 메뉴가 없다면 (AVAILABLE이 0)
        if (!hasOrderableMenu) {
            System.out.println("현재 주문 가능한 메뉴가 없습니다.");
        }
        System.out.println();
    }

    // menu.Menu Stock Read - 재고 확인 판매자용
    public void showStockStatusForSeller(int storeId) {
        // 메뉴가 아무것도 등록되지 않은 상태
        if (menus.isEmpty()) {
            System.out.println("등록된 메뉴가 없습니다. 메인 메뉴로 돌아갑니다.");
            return;
        }

        // 메뉴가 1개 이상 등록
        String storeName = storeList.findStoreById(storeId).getStoreName();
        System.out.println("\n[" + storeName + "] 지점 매장 재고 현황");
        boolean hasAvailableMenu = false; //판매중인 메뉴가 있는 확인하기 위한 flag
        for (Menu menu : menus) {
            MenuStatus status = menuStatusList.findMenuStatus(storeId, menu.getMenuId());

            // 메뉴의 상태가 존재하고 그 상태가 AVAILABLE 인 메뉴만 표시
            if (status != null && status.getStatus() == MenuSaleStatus.AVAILABLE) {
                System.out.println(menu.getMenuId() + ". " + menu.getMenuName() +
                        " | 재고 : " + status.getStock() +
                        " | 상태 : " + status.getStatus().getDisplayStatus());
                hasAvailableMenu = true; // 메뉴가 하나로 출력됐으니, flag를 ture로 변경
            }
        }

        // for 문이 끝난 후, flag가 flase라면 판매중인 메뉴가 하나도 없다는 뜻.
        if (!hasAvailableMenu) {
            System.out.println("현재 판매중인 메뉴가 없습니다.");
        }
    }

    // menu.Menu Update
    public void menuEdit(String 수정할메뉴) throws IOException {
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
                    System.out.println("  1. 커피류(coffee)\n" + "  2. 라떼류(latte)\n" + "  3. 차류(tea)");
                    System.out.print("수정할 옵션을 입력해주세요 (숫자만) : ");
                    int 수정할옵션 = sc.nextInt();
                    sc.nextLine();

                    MenuCategory menuOption = MenuCategory.fromValue(수정할옵션);
                    menus.get(i).setMenuOption(menuOption);
                    System.out.println("메뉴 옵션이 수정되었습니다.");
                    System.out.println();
                }


                checker = true;
                if (checker) {
                    Path menuFilePath = Constants.BASE_PATH.resolve("Menus.txt");
                    FileWriter writer = new FileWriter(menuFilePath.toFile());
                    for (int j = 0; j < menus.size(); j++) {
                        writer.write(menus.get(j).getMenuId() + "," + menus.get(j).getMenuName() + "," + menus.get(j).getMenuPrice() + "," + menus.get(j).getMenuOption() + "\n");
                    }
                    writer.close();
                    System.out.println("---변경사항이 저장되었습니다---");
                }
                break;
            }
        }

        if (checker == false) {
            System.out.println("입력한 메뉴명이 정확하지 않습니다. 다시 입력해주세요.");
        }
    }

    // menu.Menu Delete
    public void menuDelete(String 삭제할메뉴) throws IOException {
        boolean checker = false;

        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).getMenuName().equals(삭제할메뉴)) {
                menus.remove(i);
                System.out.println("선택한 메뉴가 삭제되었습니다.");
                checker = true;
                break;
            }
        }

        if (checker) {
            Path menuFilePath = Constants.BASE_PATH.resolve("Menus.txt");
            FileWriter writer = new FileWriter(menuFilePath.toFile());
            for (int j = 0; j < menus.size(); j++) {
                writer.write(menus.get(j).getMenuId() + "," + menus.get(j).getMenuName() + "," + menus.get(j).getMenuPrice() + "," + menus.get(j).getMenuOption() + "\n");
            }
            writer.close();
            System.out.println("---변경사항이 저장되었습니다---");
        }

        if (!checker) {
            System.out.println("입력한 메뉴명이 정확하지 않습니다. 다시 입력해주세요.");
        }

    }

    // menu.Menu Empty Check
    public boolean menuIsEmpty() {
        if (menus.isEmpty()) {
            return true;
        }
        return false;
    }

    // find menu.Menu - 매개변수와 같은 이름의 _객체 반환
    public Menu findMenu(String 주문할메뉴) {
        for (Menu menu : menus) {
            if (menu.getMenuName().equals(주문할메뉴)) {
                return menu;
            }
        }
        return null;
    }

    // menu.Menu 파일 load
    public void loadMenuFile() throws IOException {
        Path menuFilePath = Constants.BASE_PATH.resolve("Menus.txt");
        BufferedReader reader = new BufferedReader(new FileReader(menuFilePath.toFile()));

        // todo : UU id로 변경
        int maxId = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int price = Integer.parseInt(parts[2]);
            MenuCategory option = MenuCategory.valueOf(parts[3]);

            Menu menu = new Menu(id, name, price, option);
            menus.add(menu);

            maxId = Math.max(maxId, id);
        }
        reader.close();
        Menu.setNextId(maxId + 1);
    }

    // menuRecommend create
    public void menuRecommend() throws IOException {
        System.out.println();
        System.out.println("[추천 메뉴 등록 및 관리]");
        menuListCheck();
        System.out.print("등록된 메뉴 중, 추천 메뉴를 입력해주세요 : ");
        Scanner sc = new Scanner(System.in);

        String 추천메뉴명 = sc.nextLine();
        if (findMenu(추천메뉴명) != null) {
            System.out.println(findMenu(추천메뉴명).getMenuName() + "를 선택하였습니다. 추천 이유를 번호로 선택해주세요");
            System.out.println("1. Best 메뉴 선정");
            System.out.println("2. New 메뉴 선정");
            System.out.print(" : ");

            int 추천이유 = sc.nextInt();
            sc.nextLine();

            Menu 찾은메뉴 = findMenu(추천메뉴명);
            // 추천 이류를 현 객채의 Recommend 필드에 추가
            // Best 를 선택했다면,
            // todo : Enum으로 리팩토링
            if (추천이유 == 1) {
                찾은메뉴.setMenuRecommend("Best");
            } else if (추천이유 == 2) {
                찾은메뉴.setMenuRecommend("New");
            }


        } else {
            System.out.println("메뉴명이 정확하지 않습니다. 메뉴명을 정확하게 입력해주세요");
        }
    }

    // menuRecommend read
    public void menuRecommendRead() {
        System.out.println("[오늘의 추천 메뉴]");
        boolean hasBestMenu = false;
        boolean hasNewMenu = false;
        boolean checker = false;

        for (int i = 0; i < menus.size(); i++) {
            if ("Best".equals(menus.get(i).getMenuRecommend())) {
                hasBestMenu = true;
                checker = true;
            }
        }

        if (hasBestMenu == true) {
            System.out.println("[Best menu.Menu]");
            for (int i = 0; i < menus.size(); i++) {
                if ("Best".equals(menus.get(i).getMenuRecommend())) {
                    System.out.println("- " + menus.get(i).getMenuName());
                }
            }
            System.out.println();
        }

        for (int i = 0; i < menus.size(); i++) {
            if ("New".equals(menus.get(i).getMenuRecommend())) {
                hasNewMenu = true;
                checker = true;
            }
        }

        if (hasNewMenu == true) {
            System.out.println("[New menu.Menu]");
            for (int i = 0; i < menus.size(); i++) {
                if ("New".equals(menus.get(i).getMenuRecommend())) {
                    System.out.println("- " + menus.get(i).getMenuName());
                }
            }
            System.out.println();
        }

        if (checker == false) {
            System.out.println("- 오늘의 추천 메뉴가 없습니다.");
            System.out.println();
        }
    }

    // menuId로 해당 객체 반환하기
    public Menu getMenuById(int menuId) {
        for (Menu menu : menus) {
            if (menu.getMenuId() == menuId) {
                return menu;
            }
        }
        return null;
    }

    // 판매자 : 판매 메뉴 전체 read
    public void showManageableMenuList(int storeId) {
        System.out.println("[판매 메뉴 관리]");
        System.out.println("------------------------------");

        for (Menu menu : menus) {
            MenuStatus status = menuStatusList.findMenuStatus(storeId, menu.getMenuId());
            String statusText = (status != null) ? "[판매중]" : "[미판매]";

            System.out.println(menu.getMenuId() + ". " + menu.getMenuName() + " - " + statusText);
        }
        System.out.println("------------------------------");
    }
}
