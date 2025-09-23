import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

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

enum OrderStatus {
    ORDER_PLACED, PREPARING, READY, COMPLETED
}

// 상수 모음 클래스
class Constants {
    final static Path BASE_PATH = Paths.get("C:/Users/eunchan1/Desktop/whale/coding_whale/cafe_order_pjt/data");
    // 다른 방법 (, 구분)
//    final static Path BASE_PATH = Paths.get("C:", "Users", "eunchan1", "Desktop", "whale", "coding_whale", "250708_cafe_order_pjt/");
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
    private String menuOption;
    private String menuRecommend;


    public Menu() {
    }

    public Menu(String menuName, int menuPrice, String menuOption) {
        this.menuId = nextId++;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuOption = menuOption;
    }

    public Menu(int menuId, String menuName, int menuPrice, String menuOption) {
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

    public String getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(String menuOption) {
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

    public MenuList() throws IOException {
        loadMenuFile();
    }

    // Create
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
        Menu menu = new Menu(menuName, menuPrice, menuOption.name());
        menus.add(menu);

        Path menuFilePath = Constants.BASE_PATH.resolve("Menus.txt");
        FileWriter writer = new FileWriter(menuFilePath.toFile(), true);
        writer.write(menu.getMenuId() + "," + menu.getMenuName() + "," + menu.getMenuPrice() + "," + menu.getMenuOption() + "\n");
        writer.close();

        System.out.println("등록이 완료되었습니다.");
        System.out.println();
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
                    String 수정할옵션 = sc.nextLine();

                    menus.get(i).setMenuOption(수정할옵션);
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

    // Delete
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

        int maxId = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int price = Integer.parseInt(parts[2]);
            String option = parts[3];

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
}

class MenuStatus {
    private int menuId;
    private String sellerId;
    private String status;
    private int stock;

    public MenuStatus(int menuId, String sellerId, String status, int stock) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public void loadMenuStatusFile() throws  IOException {
        Path menuFilePath = Constants.BASE_PATH.resolve("Menu_status.txt");
        BufferedReader reader = new BufferedReader(new FileReader(menuFilePath.toFile()));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int menuId = Integer.parseInt(parts[0]);
            String sellerId = parts[1];
            String status = parts[2];
            int stock = Integer.parseInt(parts[3]);

            MenuStatus menuStatus = new MenuStatus(menuId, sellerId, status, stock);
            menuStatuses.add(menuStatus);
        }
        reader.close();
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
                System.out.println("-----주문 No." + (i + 1) + "-----");
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

class MyMenu {
    ArrayList<Menu> myMenu = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    // 나만의 메뉴 등록하기
    public void CreateMyManu(MenuList menuList) throws IOException {
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
        MenuList menuList = new MenuList();
        OrderHistory orderHistory = new OrderHistory();
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
                        System.out.println("3. 뒤로가기");
                        System.out.print(" : ");
                        int menuChoice = sc.nextInt();
                        sc.nextLine();

                        // 전체 메뉴 CRUD
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

                        // 판매자 계정 관리
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

                        // 관리자 모드 나가기 (뒤로 가기)
                        else if (menuChoice == 3) {
                            break;
                        }
                    }
                }

                // 판매자 모드
                case SELLER -> {
                    if (userList.sellerLogin() == null) {
                    }
                    while (true) {
                        System.out.println();
                        System.out.println("안녕하세요 사장님, 카페 주문 서비스입니다.");
                        System.out.println("1. 주문 내역 확인");
                        System.out.println("2. 추천 메뉴 등록 및 관리");
                        System.out.println("3. 로그아웃");
                        System.out.print("할 일을 선택해주세요 : ");

                        int menuSelect = sc.nextInt();
                        sc.nextLine();

                        // 주문 내역 확인
                        if (menuSelect == 1) {
                            orderHistory.OrderCheck();
                        }

                        // 추천 메뉴 등록 및 관리
                        else if (menuSelect == 2) {
                            menuList.menuRecommend();
                        }

                        // 로그아웃
                        else if (menuSelect == 3) {
                            System.out.println("로그아웃이 완료되었습니다.");
                            break;
                        }
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
                        if (userList.customerLogin() != null) {
                            while (true) {
                                System.out.println();
                                System.out.println("회원가입");

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

                                // 2-2. 주문 메뉴 확인
                                if (cusChoice == 2) {
                                    orderHistory.OrderCheck();
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
                        } else {
                            continue;
                        }
                    } else if (customerFirstChoice == 3) {
                        continue;
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

