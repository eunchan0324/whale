import constant.Constants;

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
// todo : User Menu Order 클래스 패키지로 빼기 / 하나의 파일 안에 하나의 클래스를 넣는 것을 먼저.

enum UserRole {
    ADMIN(1, "관리자"),
    SELLER(2, "판매자"),
    CUSTOMER(3, "구매자"),
    UNROLE(4, "UNROLE"),
    ;

    private final int value;
    private final String role;

    UserRole(int value, String korean) {
        this.value = value;
        this.role = korean;
    }

    public int getValue() {
        return value;
    }

    public String getRole() {
        return role;
    }

    public static UserRole fromValue(int value) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        return UNROLE;
    }

}

enum MenuCategory {
    COFFEE(1),
    LATTE(2),
    TEA(3);

    private final int value;

    MenuCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static MenuCategory fromValue(int value) {
        for (MenuCategory category : MenuCategory.values()) {
            if (category.getValue() == value) {
                return category;
            }
        }
        return null;
    }
}

enum MenuSaleStatus {
    AVAILABLE,
    SOLD_OUT
}

enum OrderStatus {
    ORDER_PLACED(0),
    PREPARING(1),
    READY(2),
    COMPLETED(3);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderStatus fromValue(int value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }
}

class User {
    private String id;
    private String password;
    private UserRole role;

    User(String id, String password, UserRole role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", password='" + password + '\'' + ", role='" + role + '\'' + '}';
    }
}

class UserList {
    ArrayList<User> adminList = new ArrayList<>();
    ArrayList<User> sellerList = new ArrayList<>();
    ArrayList<User> customerList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    // 프로그램 시작 시, 모든 계정 정보 LOAD
    UserList() throws IOException {
        loadSellerFile();
        loadAdminFile();
        loadCustomerFile();
    }

    // 회원가입 - 역할 기반
    public void registerUser(UserRole role, ArrayList<User> targetList, String fileName) throws IOException {
        System.out.println();
        System.out.println("[" + role + " 회원가입]");

        String thisId;
        String thisPassword;

        // id
        while (true) {
            System.out.print("ID를 입력해주세요 (4~12자 사이 / 영문,숫자만 허용) : ");
            String id = sc.nextLine();

            // id check 실패
            if (!idDuplicateCheck(id, targetList)) {
                System.out.println("중복된 ID입니다. 다시 입력해주세요");
                continue;
            }

            // 유효성 검사
            if (!idInvalidCheck(id)) {
                System.out.println("유효하지 않은 ID입니다. 4~12자 사이로 입력해주세요.");
                continue;
            }

            // id check 성공
            System.out.println("사용가능한 ID입니다.");
            thisId = id;
            break;
        }

        // password
        while (true) {
            System.out.print("Password를 입력해주세요 (길이 8자 이상 / 영문 + 숫자 조합만 허용) : ");
            String password = sc.nextLine();

            // pw check 실패
            if (!passwordInvalidCheck(password)) {
                System.out.println("유효하지 않은 Password입니다. 8자 이상, 영문+숫자 조합으로 입력해주세요.");
                continue;
            }

            // pw check 성공
            System.out.println("사용가능한 Password입니다.");
            thisPassword = password;
            break;

        }

        // role
//        while (true) {
//            System.out.println("사장님인가요 손님인가요?");
//            System.out.println("1. 사장");
//            System.out.println("2. 손님");
//            System.out.print(" : ");
//
//            int whatRole = sc.nextInt();
//            sc.nextLine();
//
//            if (whatRole == 1) {
//                thisRole = UserRole.SELLER;
//                break;
//            } else if (whatRole == 2) {
//                thisRole = UserRole.CUSTOMER;
//                break;
//            } else {
//                System.out.println("정확한 숫자를 입력해주세요.");
//            }
//        }

        User user = new User(thisId, thisPassword, role);
        targetList.add(user);

        Path userFilePath = Constants.BASE_PATH.resolve(fileName);
        FileWriter writer = new FileWriter(userFilePath.toFile(), true);
        writer.write(user.getId() + "," + user.getPassword() + "," + user.getRole() + "\n");
        writer.close();
        System.out.println(role.getRole() + " 회원가입이 완료되었습니다.");
    }

    // id 중복 확인
    public Boolean idDuplicateCheck(String id, ArrayList<User> targetList) {
        boolean idchecker = true;

        // 중복 확인
        for (int i = 0; i < targetList.size(); i++) {
            if (id.equals(targetList.get(i).getId())) {
                System.out.println();
                idchecker = false;
            }
        }

        return idchecker;
    }

    // id 유효성 검사
    // todo : 정규식 리팩토링
    // todo :  return으로 리팩토링
    public Boolean idInvalidCheck(String id) {
        boolean idchecker = true;
        if (id.length() < 4 || id.length() > 12) {
            idchecker = false;
        }

        boolean 영문있음 = false;
        boolean 숫자있음 = false;
        for (int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                영문있음 = true;
            }

            if (c >= '0' && c <= '9') {
                숫자있음 = true;
            }
        }

        if (영문있음 && 숫자있음) {
            return idchecker;
        } else {
            return false;
        }
    }

    // password 유효성 검사
    public Boolean passwordInvalidCheck(String password) {
        boolean passwordCehcker = true;

        if (password.length() < 8) {
            passwordCehcker = false;
        }

        boolean 영문있음 = false;
        boolean 숫자있음 = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                영문있음 = true;
            }

            if (c >= '0' && c <= '9') {
                숫자있음 = true;
            }
        }

        if (영문있음 && 숫자있음) {
            return passwordCehcker;
        } else {
            return false;
        }
    }

    // 로그인 로직
    public User performLogin(ArrayList<User> targetList, String systemName) {
        System.out.println();
        System.out.println("[" + systemName + " 로그인]");
        while (true) {
            System.out.println("1. 로그인");
            System.out.println("2. 뒤로가기");
            System.out.print(" : ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                boolean loginSuccess = false;
                boolean idChecker = false;
                boolean pwChecker = false;

                System.out.println();
                System.out.print("id를 입력해주세요 : ");
                String id = sc.nextLine();

                System.out.print("password를 입력해주세요 : ");
                String password = sc.nextLine();

                if (targetList.isEmpty()) {
                    System.out.println("유효하지 않은 id/pw입니다.");
                    System.out.println("회원가입을 먼저 진행해주세요.");
                }

                User 찾은사용자 = findUser(id, targetList);

                if (찾은사용자 != null) {
                    // 입력한 pw가 없다면?
                    if (!password.equals(찾은사용자.getPassword())) {
                        pwChecker = true;
                    } else {
                        pwChecker = false;
                    }
                } else {
                    System.out.println("유효하지 않은 id입니다. 다시 입력해주세요.");
                    idChecker = true;
                }

                if (pwChecker) {
                    System.out.println("유효하지 않은 password입니다. 다시 입력해주세요.");
                }

                if (!idChecker && !pwChecker) {
                    loginSuccess = true;
                }

                if (loginSuccess) {
                    System.out.println("로그인이 완료되었습니다!");
                    return 찾은사용자;
                }
            } else if (choice == 2) {
                return null;
            } else {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }

    // 관리자 로그인
    public User adminLogin() {
        return performLogin(adminList, "관리자");
    }

    // 판매자 로그인
    public User sellerLogin() {
        return performLogin(sellerList, "판매자");
    }

    // 구매자 로그인
    public User customerLogin() {
        return performLogin(customerList, "구매자");
    }

    // 판매자 계정 조회(read)
    public void sellerAccountRead() {

        if (!sellerList.isEmpty()) {
            for (int i = 0; i < sellerList.size(); i++) {
                System.out.println(sellerList.get(i));
            }
        } else {
            System.out.println("판매자 계정이 존재하지 않습니다.");
        }
    }

    // 판매자 계정 수정(update)
    public void sellerAccountUpdate() throws IOException {
        System.out.println("[판매자 계정 수정]");
        sellerAccountRead();

        while (true) {
            System.out.print("수정을 원하는 id를 입력해주세요 : ");
            String id = sc.nextLine();
            boolean checker = false;

            for (int i = 0; i < sellerList.size(); i++) {
                if (id.equals(sellerList.get(i).getId())) {
                    System.out.println("입력한 id 계정 정보 : " + sellerList.get(i));
                    System.out.print("변경할 비밀번호를 입력해주세요 : ");
                    String newPassword = sc.nextLine();

                    sellerList.get(i).setPassword(newPassword);
                    Path sellerFilePath = Constants.BASE_PATH.resolve("Seller.txt");
                    FileWriter writer = new FileWriter(sellerFilePath.toFile());
                    for (int j = 0; j < sellerList.size(); j++) {
                        writer.write(sellerList.get(j).getId() + "," + sellerList.get(j).getPassword() + "," + sellerList.get(j).getRole() + "\n");
                    }
                    writer.close();
                    System.out.println("수정이 완료되었습니다.");
                    checker = true;
                    break;
                }
            }

            if (checker == true) {
                break;
            }

            if (checker == false) {
                System.out.println("입력한 id가 정확하지 않습니다.");
                System.out.println("1. id 다시 입력하기");
                System.out.println("2. 나가기");
                System.out.print(" : ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 2) {
                    break;
                }
            }
        }
    }

    // 판매자 계정 삭제(delete)
    public void sellerAccountDelete() throws IOException {
        System.out.println("[판매자 계정 삭제]");

        while (true) {
            sellerAccountRead();
            System.out.print("삭제를 원하는 id를 입력해주세요 : ");
            String id = sc.nextLine();
            boolean checker = false;
            boolean shouldExit = false;

            for (int i = 0; i < sellerList.size(); i++) {
                if (id.equals(sellerList.get(i).getId())) {
                    System.out.println("입력한 id 계정 정보 : " + sellerList.get(i));
                    System.out.println("정말 삭제하시겠습니까?");
                    System.out.println("1. 네");
                    System.out.println("2. 아니요");
                    System.out.print(" : ");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    if (choice == 1) {
                        sellerList.remove(i);

                        Path sellerFilePath = Constants.BASE_PATH.resolve("Seller.txt");
                        FileWriter writer = new FileWriter(sellerFilePath.toFile());
                        for (int j = 0; j < sellerList.size(); j++) {
                            writer.write(sellerList.get(j).getId() + "," + sellerList.get(j).getPassword() + "," + sellerList.get(j).getRole() + "\n");
                        }
                        writer.close();
                        checker = true;
                        break;
                    } else if (choice == 2) {
                        System.out.println("1. 다른 ID 입력");
                        System.out.println("2. 나가기");
                        System.out.print(" : ");
                        int choice2 = sc.nextInt();
                        sc.nextLine();

                        if (choice2 == 1) {
                            break;
                        } else if (choice2 == 2) {
                            return;
                        }
                    }

                    if (shouldExit) {
                        break;
                    }
                }
            }

            if (checker) {
                System.out.println("삭제가 완료되었습니다.");
                break;
            }

            if (checker == false) {
                System.out.println("입력한 id가 유효하지 않습니다.");
                System.out.println("1. id 재입력");
                System.out.println("2. 나가기");
                System.out.print(" : ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 2) {
                    break;
                }
            }
        }
    }


    // id,tarList에 맞는 객체 찾기
    public User findUser(String id, ArrayList<User> targetList) {
        for (int i = 0; i < targetList.size(); i++) {
            if (targetList.get(i).getId().equals(id)) {
                return targetList.get(i);
            }
        }
        return null;
    }

    // Seller 파일 load + SellerList add
    public void loadSellerFile() throws IOException {
        Path userFilePath = Constants.BASE_PATH.resolve("Seller.txt");
        BufferedReader reader = new BufferedReader((new FileReader(userFilePath.toFile())));


        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String id = parts[0];
            String password = parts[1];
            UserRole role = UserRole.valueOf(parts[2]);

            User user = new User(id, password, role);
            sellerList.add(user);
        }
        reader.close();
    }

    // Admin 파일 load + adminlist에 업로드
    public void loadAdminFile() throws IOException {
        Path adminFilePath = Constants.BASE_PATH.resolve("Admin.txt");
        BufferedReader reader = new BufferedReader((new FileReader(adminFilePath.toFile())));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String id = parts[0];
            String pw = parts[1];
            UserRole role = UserRole.valueOf(parts[2]);

            User user = new User(id, pw, role);
            adminList.add(user);
        }
        reader.close();
    }

    // Customer.txt load + customerList add
    public void loadCustomerFile() throws IOException {
        Path adminFilePath = Constants.BASE_PATH.resolve("Customer.txt");
        BufferedReader reader = new BufferedReader((new FileReader(adminFilePath.toFile())));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String id = parts[0];
            String pw = parts[1];
            UserRole role = UserRole.valueOf(parts[2]);

            User user = new User(id, pw, role);
            customerList.add(user);
        }
        reader.close();
    }


}

class Menu {
    private static int nextId = 1;
    private int menuId;
    private String menuName;
    private int menuPrice;
    private MenuCategory menuOption;
    private String menuRecommend;


    public Menu() {
    }

    public Menu(String menuName, int menuPrice, MenuCategory menuOption) {
        this.menuId = nextId++;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuOption = menuOption;
    }

    public Menu(int menuId, String menuName, int menuPrice, MenuCategory menuOption) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuOption = menuOption;
    }

    public Menu(String recomReason) {
        this.menuRecommend = recomReason;
    }

    public int getMenuId() {
        return menuId;
    }

    public static void setNextId(int nextId) {
        Menu.nextId = nextId;
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

    public MenuCategory getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(MenuCategory menuOption) {
        this.menuOption = menuOption;
    }

    public String getMenuRecommend() {
        return menuRecommend;
    }

    public void setMenuRecommend(String menuRecommend) {
        this.menuRecommend = menuRecommend;
    }

    @Override
    public String toString() {
        return "[1.메뉴 이름 = " + menuName + ", 2.가격 = " + menuPrice + '원' + ", 3.옵션 = " + menuOption + ", 4.추천 여부 = " + menuRecommend + ']';
    }
}

// todo : 1. 각 기능별 role에 따라 인가 가능한 것 체크하기 + 일단 User에 있는 맴버 변수를 가져와서 비교해보기
// todo : 2. 인가 기능을 따로 뺄 수 있는지 확인해보기
class MenuList {
    private final ArrayList<Menu> menus = new ArrayList<>();
    private MenuStatusList menuStatusList;

    public MenuList(MenuStatusList menuStatusList) throws IOException {
        this.menuStatusList = menuStatusList;
        loadMenuFile();
    }

    // Menu Create
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

    // Menu Read - 관리자용
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

    // Menu Read - 구매자용
    public void menuListCheck(String sellerId) {
        // 메뉴가 아무것도 등록되지 않았다면
        if (menus.isEmpty()) {
            System.out.println("등록된 메뉴가 없습니다. 메인 메뉴로 돌아갑니다.");
            System.out.println();
        }

        // 메뉴가 1개 이상 등록되어있다면
        else {
            System.out.println("[현재 등록된 메뉴 목록]");
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                int menuId = menu.getMenuId();

                String menuInfo = menu.getMenuId() + ". " + menu.getMenuName() + " | " + menu.getMenuPrice() + "원";

                if (!menuStatusList.isAvailable(sellerId, menuId)) {
                    menuInfo += "(품절)";
                }

                System.out.println(menuInfo);
            }
            System.out.println();
        }
    }

    // Menu Stock Read - 재고 확인 판매자용
    public void showStockStatusForSeller(String sellerId) {
        if (menus.isEmpty()) {
            System.out.println("등록된 메뉴가 없습니다. 메인 메뉴로 돌아갑니다.");
            System.out.println();
        }

        // 메뉴가 1개 이상 등록되어있다면
        else {
            System.out.println("\n[" + sellerId + "] 님 매장 재고 현황");
            for (Menu menu : menus) {
                MenuStatus status = menuStatusList.findMenuStatus(sellerId, menu.getMenuId());

                if (status != null) {
                    System.out.println(menu.getMenuId() + ". " + menu.getMenuName() +
                            " | 재고 : " + status.getStock() +
                            " | 상태 : " + status.getStatus());
                } else {
                    System.out.println(menu.getMenuId() + ". " + menu.getMenuName() +
                            " | 재고 : (미등록) " +
                            " | 상태 : (미등록)");
                }
            }
        }
    }

    // Menu Update
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

    // Menu Delete
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

    // Menu Empty Check
    public boolean menuIsEmpty() {
        if (menus.isEmpty()) {
            return true;
        }
        return false;
    }

    // find Menu - 매개변수와 같은 이름의 _객체 반환
    public Menu findMenu(String 주문할메뉴) {
        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).getMenuName().equals(주문할메뉴)) {
                return menus.get(i);
            }
        }
        return null;
    }

    // Menu 파일 load
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
            System.out.println("[Best Menu]");
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
            System.out.println("[New Menu]");
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
    public void showManageableMenuList(String sellerId) {
        System.out.println("[판매 메뉴 관리]");
        System.out.println("------------------------------");

        for (Menu menu : menus) {
            MenuStatus status = menuStatusList.findMenuStatus(sellerId, menu.getMenuId());
            String statusText = (status != null) ? "[판매중]" : "[미판매]";

            System.out.println(menu.getMenuId() + ". " + menu.getMenuName() + " - " + statusText);
        }
        System.out.println("------------------------------");
    }
}

class MenuStatus {
    private int menuId;
    private String sellerId;
    private MenuSaleStatus status;
    private int stock;

    public MenuStatus(int menuId, String sellerId, MenuSaleStatus status, int stock) {
        this.menuId = menuId;
        this.sellerId = sellerId;
        this.status = status;
        this.stock = stock;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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
                "menuId=" + menuId +
                ", sellerId='" + sellerId + '\'' +
                ", status='" + status + '\'' +
                ", stock=" + stock +
                '}';
    }

}

class MenuStatusList {
    ArrayList<MenuStatus> menuStatuses = new ArrayList<>();

    public void saveMenuStatusFile() throws IOException {
        Path menuFilePath = Constants.BASE_PATH.resolve("Menu_status.txt");
        FileWriter writer = new FileWriter(menuFilePath.toFile());

        for (int i = 0; i < menuStatuses.size(); i++) {
            MenuStatus status = menuStatuses.get(i);
            writer.write(status.getMenuId() + "," +
                    status.getSellerId() + "," +
                    status.getStatus() + "," +
                    status.getStock() + "\n");
        }
        writer.close();
    }

    public void loadMenuStatusFile() throws IOException {
        Path menuFilePath = Constants.BASE_PATH.resolve("Menu_status.txt");
        BufferedReader reader = new BufferedReader(new FileReader(menuFilePath.toFile()));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int menuId = Integer.parseInt(parts[0]);
            String sellerId = parts[1];
            MenuSaleStatus status = MenuSaleStatus.valueOf(parts[2]);
            int stock = Integer.parseInt(parts[3]);

            MenuStatus menuStatus = new MenuStatus(menuId, sellerId, status, stock);
            menuStatuses.add(menuStatus);
        }
        reader.close();
    }

    // 생성자 - loadMenuStatusFile()
    public MenuStatusList() throws IOException {
        loadMenuStatusFile();
    }

    // 판매자 id로 MenuStatus 객체 반환
    public MenuStatus findMenuStatus(String sellerId, int menuId) {
        for (int i = 0; i < menuStatuses.size(); i++) {
            if (menuStatuses.get(i).getSellerId().equals(sellerId) && menuStatuses.get(i).getMenuId() == menuId) {
                return menuStatuses.get(i);
            }
        }
        return null;
    }

    // 재고 수량 변경
    public void updateStock(String sellerId, int menuId, int newStock) throws IOException {

        MenuStatus menuStatus = findMenuStatus(sellerId, menuId);

        // 재고 정보가 있으면 : 기본 재고 정보 수정(update)
        if (menuStatus != null) {
            menuStatus.setStock(newStock);
            System.out.println("재고가 성공적으로 업데이트되었습니다.");
        }

        // 재고 정보가 없으면 :
        else {
            MenuStatus newMenuStatus = new MenuStatus(menuId, sellerId, MenuSaleStatus.AVAILABLE, newStock);
            menuStatuses.add(newMenuStatus);
            System.out.println("새로운 메뉴의 재고가 등록되었습니다.");
        }

        saveMenuStatusFile(); // 파일 저장

    }

    // 판매 상태 변경
    public void updateStatus(String sellerId, int menuId, MenuSaleStatus newStatus) throws IOException {
        MenuStatus menuStatus = findMenuStatus(sellerId, menuId);

        // 판매 상태가 있다면 :
        if (menuStatus != null) {
            menuStatus.setStatus(newStatus);
            System.out.println("메뉴 상태가 " + newStatus.name() + "(으)로 변경되었습니다.");
        }

        // 판매 상태가 없다면 (null) :
        else {
            MenuStatus newMenuStatus = new MenuStatus(menuId, sellerId, newStatus, 0); // 기본 재고 0으로 등록
            menuStatuses.add((newMenuStatus));
            System.out.println("새로운 메뉴의 상태가 " + newStatus.name() + "(으)로 등록되었습니다.");
        }

        saveMenuStatusFile(); // 파일 저장
    }


    /**
     * 지정된 메뉴의 재고를 1감소 시키는 메서드
     * 이 메서드를 호출하기 전에는 반드시 isAvailable()로 재고를 확인해야 함
     *
     * @param sellerId 판매자 ID
     * @param menuId   메뉴 ID
     * @return 성공하면 ture, 메뉴를 찾지 못하면 false
     * @throws IOException
     */
    public boolean decreaseStock(String sellerId, int menuId) throws IOException {

        MenuStatus menuStatus = findMenuStatus(sellerId, menuId);

        if (menuStatus == null) {
            System.out.println("시스템 오류 : 해당 메뉴의 재고 정보를 찾을 수 없습니다.");
            return false;
        }

        int currentStock = menuStatus.getStock();
        menuStatus.setStock(currentStock - 1);

        saveMenuStatusFile();
        return true;
    }

    // 판매가 가능한 상황인지 확인 (재고0이상 / 상태 AVAILABLE)
    public boolean isAvailable(String sellerId, int menuId) {
        MenuStatus menuStatus = findMenuStatus(sellerId, menuId);

        if (menuStatus == null) {
            return false;
        }

        // 조건식이 true 또는 false를 반환
        return menuStatus.getStock() > 0 && menuStatus.getStatus() == MenuSaleStatus.AVAILABLE;

    }

    // 판매 메뉴 등록
    public void registerMenuForSale(String sellerId, int menuId) throws IOException {
        // 1. 이미 등록된 메뉴인지 먼저 확인
        if (findMenuStatus(sellerId, menuId) != null) {
            System.out.println("이미 판매 목록에 등록된 메뉴입니다.");
            return;
        }

        // 2. 등록되지 않은 메뉴라면, 새로운 MenuStatus 객체 생성
        // 초기 재고는 0개, 판매 상태는 AVAILABLE
        MenuStatus newMenuStatus = new MenuStatus(menuId, sellerId, MenuSaleStatus.AVAILABLE, 0);

        // 3. 리스트에 추가
        menuStatuses.add(newMenuStatus);

        // 4. 파일에 저장
        saveMenuStatusFile();
        System.out.println("성공적으로 판매 메뉴에 등록되었습니다. 재고 관리를 통해 수량을 조절해주세요.");
    }

    // 판매 메뉴 삭제
    public void removeMenuForSale(String sellerId, int menuId) throws IOException {
        MenuStatus targetMenuState = findMenuStatus(sellerId, menuId);

        if (targetMenuState == null) {
            System.out.println("존재하지 않는 메뉴 ID입니다. 다시 입력해주세요");
            return;
        }

        menuStatuses.remove(targetMenuState);

        saveMenuStatusFile();
        System.out.println("성공적으로 판매 메뉴가 삭제되었습니다.");
    }

}

class Order {
    private int orderId;
    private String customerId;
    private String sellerId;
    private LocalDateTime orderTime;
    private int totalPrice;
    private OrderStatus status;
    private final ArrayList<OrderItem> items = new ArrayList<>();

    public Order(String customerId, String sellerId, int totalPrice, OrderStatus status, ArrayList<OrderItem> items) {
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.orderTime = LocalDateTime.now();
        this.totalPrice = totalPrice;
        this.status = status;
        this.items.addAll(items);
    }

    public Order(int orderId, String customerId, String sellerId, LocalDateTime orderTime, int totalPrice, OrderStatus status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.sellerId = sellerId;
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

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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

class OrderItem {
    private Menu menu;
    private int finalPrice;
    private String finalOptions;
    private String finalCup;
    private String finalTemp;

    public OrderItem(Menu menu) {
        this.menu = menu;
    }

    public OrderItem(Menu menu, int finalPrice, String finalOptions, String finalCup, String finalTemp) {
        this.menu = menu;
        this.finalPrice = finalPrice;
        this.finalOptions = finalOptions;
        this.finalCup = finalCup;
        this.finalTemp = finalTemp;
    }


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

        if (menu.getMenuOption() == MenuCategory.COFFEE) {

            System.out.println("샷 옵션을 선택해주세요");
            System.out.println("1.기본(2샷) " + System.lineSeparator() + "2.연하게(1샷)" + System.lineSeparator() + "3.샷추가(3샷) + 500원" + System.lineSeparator() + "4.디카페인 + 1000원");
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
        } else if (menu.getMenuOption() == MenuCategory.LATTE) {
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
        } else if (menu.getMenuOption() == MenuCategory.TEA) {
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
        System.out.println("[주문 내역 확인]");
        System.out.println("메뉴명 : " + menu.getMenuName());
        System.out.println("온도 : " + finalTemp);
        System.out.println("사이즈 : " + finalCup);
        System.out.println("옵션 : " + finalOptions);
        System.out.println("총 가격 : " + finalPrice + "원");
        System.out.println();
    }
}

class OrderList {
    private ArrayList<Order> orderList = new ArrayList<>();
    private int nextOrderId = 1;
    private MenuList menuList;

    public OrderList(MenuList menuList) throws IOException {
        this.menuList = menuList;
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
                    order.getSellerId() + "," +
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
            String sellerId = parts[2];
            LocalDateTime orderTime = LocalDateTime.parse(parts[3]);
            int totalPrice = Integer.parseInt(parts[4]);
            OrderStatus status = OrderStatus.valueOf(parts[5]);

            // 임시 객체 생성자
            Order order = new Order(orderId, customerId, sellerId, orderTime, totalPrice, status);
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

            //2. OrderItem 생성
            Menu menu = menuList.getMenuById(menuId); // menuId로 menu 객체 찾기
            OrderItem item = new OrderItem(menu, price, temp, cup, options);

            // 3. Order에 OrderItem 추가
            targetOrder.getItems().add(item);

        }
        orderItemsReader.close();

        // 모든 로딩이 끝난 후, Map의 모든 Order 객체를 실제 orderList에 추가
        orderList.addAll(ordersMap.values());
    }

    public void checkOrders() {
        if (orderList.isEmpty()) {
            System.out.println("현재 주문 내역이 존재하지 않습니다.");
            return;
        }


        System.out.println("--- 전체 주문 내역 --- ");
        // 1. 바깥쪽 루프 : 전체 주문(Order) 목록 순회
        for (Order order : orderList) {
            System.out.println("===================================");
            System.out.println("주문 번호 : " + order.getOrderId());
            System.out.println("주문 시간 : " + order.getOrderTime());
            System.out.println("주문 상태 : " + order.getStatus());
            System.out.println("--- 주문 메뉴 목록 ---");

            // 2. 안쪽 루프 : 해당 주문(Order)에 포함된 메뉴(OrderItem) 목록 순회
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

                // 2. 안쪽 루프 : 해당 주문(Order)에 포함된 메뉴(OrderItem) 목록 순회
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

    public void showMySales(String sellerId) {
        System.out.println("[" + sellerId + "님 지점 매출 조회]");

        int totalPrice = 0;
        for (Order order : orderList) {
            if (order.getStatus() == OrderStatus.COMPLETED && order.getSellerId().equals(sellerId)) {
                totalPrice += order.getTotalPrice();
            }
        }

        System.out.println(sellerId + "님 지점 전체 매출 : " + totalPrice + "원");
    }

    public void showSalesBySeller() {
        System.out.println("[지점별 매출 현황]");

        // 1. sellerId(String)을 key로, 누적 매출액(interger)을 value로 갖는 Map 생성
        Map<String, Integer> salesBySeller = new HashMap<>();

        // 2. 전체 주문 목록 순회하며 COMPLETED order 객체 찾기
        for (Order order : orderList) {
            if (order.getStatus() == OrderStatus.COMPLETED) {
                // 3. salesBySeller Map에 추가
                String sellerId = order.getSellerId();
                int price = order.getTotalPrice();

                Integer currentSales = salesBySeller.get(sellerId);

                if (currentSales == null) {
                    salesBySeller.put(sellerId, price);
                } else {
                    salesBySeller.put(sellerId, price + currentSales);
                }


            }
        }

        // 4. 데이터가 모두 쌓인 Map을 출력
        System.out.println("--------------------");
        for (Map.Entry<String, Integer> entry : salesBySeller.entrySet()) {
            System.out.println("- " + entry.getKey() + " : " + entry.getValue() + "원");
        }
        System.out.println("--------------------");
    }

}

class MyMenu {
    ArrayList<Menu> myMenu = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    // 나만의 메뉴 등록하기
    public void CreateMyManu(MenuList menuList) {
        boolean hasMyMenu = false;
        System.out.println();
        System.out.println("[나만의 메뉴 등록하기]");
        menuList.menuListCheck();
        System.out.print("메뉴 목록 중, 나만의 메뉴로 등록할 메뉴 명을 입력해주세요 : ");
        String 입력한메뉴명 = sc.nextLine();
        Menu 찾은메뉴 = menuList.findMenu(입력한메뉴명);

        // 중복체크
        // todo : 메뉴의 동등성 비교 학습 (이 코드를 다르게 하는 방법)
        for (int i = 0; i < myMenu.size(); i++) {
            if (입력한메뉴명.equals(myMenu.get(i).getMenuName())) {
                hasMyMenu = true;
            }
        }

        // 유효성 체크 - 실제로 메뉴가 존재하는가?
        if (찾은메뉴 != null && hasMyMenu == false) {
            myMenu.add(찾은메뉴);
            System.out.println("등록이 완료되었습니다.");
            System.out.println();
        } else {
            System.out.println("메뉴명이 정확하지 않습니다. 다시 입력해주세요.");
        }

        if (hasMyMenu == true) {
            System.out.println("이미 존재하는 나만의 메뉴입니다.");
            System.out.println();
        }
    }

    // 찜한 메뉴 보기
    public void ReadMyMenu() {
        System.out.println();
        System.out.println("[나만의 메뉴 목록]");

        if (myMenu.isEmpty()) {
            System.out.println("등록된 나만의 메뉴가 없습니다.");
        } else {
            for (int i = 0; i < myMenu.size(); i++) {
                System.out.println(myMenu.get(i));
            }
        }
        System.out.println();
    }

    // 찜 취소하기
    public void DeleteMyMenu() {
        System.out.println();
        System.out.println("[나만의 메뉴 삭제]");
        if (myMenu.isEmpty()) {
            System.out.println("등록된 나만의 메뉴가 없습니다.");
        } else {
            for (int i = 0; i < myMenu.size(); i++) {
                System.out.println(myMenu.get(i));
            }

            System.out.print("삭제할 나만의 메뉴명을 입력해주세요 : ");
            String 삭제할메뉴명 = sc.nextLine();

            for (int i = 0; i < myMenu.size(); i++) {
                if (삭제할메뉴명.equals(myMenu.get(i).getMenuName())) {
                    myMenu.remove(i);
                    System.out.println("입력한 나만의 메뉴가 삭제되었습니다.");
                    System.out.println();
                } else {
                    System.out.println("삭제할 메뉴명이 정확하지 않습니다. 다시 입력해주세요.");
                    System.out.println();
                }
            }

        }
    }
}


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
//        Menu menu = new Menu();
        // 객체 생성 == MenuList의 인스턴스 menuList 생성

        UserList userList = new UserList();
        MenuStatusList menuStatusList = new MenuStatusList();
        MenuList menuList = new MenuList(menuStatusList);
        OrderList orderList = new OrderList(menuList);
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
                        System.out.println("4. 뒤로가기");
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
                                userList.registerUser(UserRole.SELLER, userList.sellerList, "Seller.txt");
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

                        // 4. 관리자 모드 나가기 (뒤로 가기)
                        else if (menuChoice == 4) {
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

                            String sellerId = loggendinSeller.getId();

                            while (true) {
                                System.out.println();
                                System.out.println("안녕하세요 " + sellerId + "님, 카페 주문 서비스입니다.");
                                System.out.println("1. 주문 관리");
                                System.out.println("2. 추천 메뉴 관리");
                                System.out.println("3. 재고 관리");
                                System.out.println("4. 매출 조회");
                                System.out.println("5. 판매 메뉴 관리");
                                System.out.println("5. 로그아웃");
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
                                            menuList.showStockStatusForSeller(sellerId);
                                        }

                                        // 3-2. 재고 수량 변경
                                        else if (choice == 2) {
                                            System.out.println("[재고 수량 변경]");
                                            menuList.showStockStatusForSeller(sellerId);

                                            System.out.print("수정할 메뉴의 ID를 입력해주세요 : ");
                                            int menuId = sc.nextInt();
                                            sc.nextLine();

                                            System.out.print("수정할 재고의 수량을 입력해주세요 : ");
                                            int newStock = sc.nextInt();
                                            sc.nextLine();

                                            menuStatusList.updateStock(sellerId, menuId, newStock);
                                        }

                                        // 3-3. 판매 상태 변경
                                        else if (choice == 3) {
                                            System.out.println();
                                            System.out.println("[판매 상태 변경]");
                                            menuList.showStockStatusForSeller(sellerId);

                                            System.out.print("수정할 메뉴의 ID를 입력해주세요 : ");
                                            int menuId = sc.nextInt();
                                            sc.nextLine();

                                            System.out.print("수정할 메뉴의 상태를 입력해주세요");
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
                                                menuStatusList.updateStatus(sellerId, menuId, newstatus);
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
                                    orderList.showMySales(sellerId);
                                }

                                // 5. 판매 메뉴 관리
                                else if (menuSelect == 5) {
                                    menuList.showManageableMenuList(sellerId);
                                    System.out.println();
                                    System.out.println("1. 판매 메뉴 등록하기");
                                    System.out.println("2. 판매 메뉴 삭제하기");
                                    System.out.println("3. 뒤로가기");
                                    int salesChoice = sc.nextInt();
                                    sc.nextLine();

                                    // 5-1. 판매 메뉴 등록하기
                                    if (salesChoice == 1) {
                                        System.out.print("등록할 메뉴의 ID를 입력해주세요 : ");
                                        int menuId = sc.nextInt();
                                        sc.nextLine();

                                        menuStatusList.registerMenuForSale(sellerId, menuId);
                                    }

                                    // 5-2. 판매 메뉴 삭제하기
                                    else if (salesChoice == 2) {
                                        System.out.print("삭제할 메뉴의 ID를 입력해주세요 : ");
                                        int menuId = sc.nextInt();
                                        sc.nextLine();

                                        menuStatusList.removeMenuForSale(sellerId, menuId);
                                    }

                                    // 5-3. 뒤로가기
                                    else if (salesChoice == 3) {
                                        break;
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
                        userList.registerUser(UserRole.CUSTOMER, userList.customerList, "Customer.txt");
                    }

                    // 로그인
                    else if (customerFirstChoice == 2) {
                        User loggedInCustomer = userList.customerLogin();
                        // 로그인 성공
                        if (loggedInCustomer != null) {
                            while (true) {
                                // todo : 하드코딩 리팩토링
                                String sellerId = "dmscks1";

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
                                    // 1. 장바구니 준비
                                    ArrayList<OrderItem> cart = new ArrayList<>();

                                    // 2. 메뉴 담기 루프
                                    while (true) {
                                        // 메뉴 없을 때
                                        if (menuList.menuIsEmpty()) {
                                            System.out.println("등록된 메뉴가 없습니다. 메인 메뉴로 돌아갑니다.");
                                            break;
                                        }

                                        // 메뉴 있을 때
                                        System.out.println("[전체 메뉴]");
                                        menuList.menuListCheck(sellerId);
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
                                            if (menuStatusList.isAvailable(sellerId, targetMenu.getMenuId())) {
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

                                    // 3. 주문 처리 (루프가 끝난 후)
                                    if (!cart.isEmpty()) {
                                        // 3-1. 총액 계산 및 Order 객체 생성
                                        int totalPirce = 0;
                                        for (OrderItem item : cart) {
                                            totalPirce += item.getFinalPrice(); //
                                        }

                                        Order finalOrder = new Order(loggedInCustomer.getId(), sellerId, totalPirce, OrderStatus.ORDER_PLACED, cart);

                                        // 3-2. 주문 내역에 '최종 주문서 추가'
                                        orderList.addOrder(finalOrder);

                                        // 3-3. 재고 감소
                                        for (OrderItem item : cart) {
                                            menuStatusList.decreaseStock(sellerId, item.getMenu().getMenuId());
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

