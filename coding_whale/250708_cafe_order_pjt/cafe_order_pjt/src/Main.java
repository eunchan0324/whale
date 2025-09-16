import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

// 상수 모음 클래스
class Constants {
    final static Path BASE_PATH = Paths.get("C:/Users/eunchan1/Desktop/whale/coding_whale/250708_cafe_order_pjt/");
    // 다른 방법
//    final static Path BASE_PATH = Paths.get("C:", "Users", "eunchan1", "Desktop", "whale", "coding_whale", "250708_cafe_order_pjt/");
}

class User {
    private String id;
    private String password;
    private String role;

    User(String id, String password, String role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public User() {

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", password='" + password + '\'' + ", role='" + role + '\'' + '}';
    }
}

class UserList {
    ArrayList<User> userlist = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    UserList() throws IOException {
        loadUserFile();
    }


    // 회원가입
    public void registerUser() throws IOException {
        System.out.println();
        System.out.println("[회원가입]");

        String thisId;
        String thisPassword;
        String thisRole;

        // id
        while (true) {
            System.out.print("ID를 입력해주세요 (4~12자 사이 / 영문,숫자만 허용) : ");
            String id = sc.nextLine();

            // id check 실패
            if (!idDuplicateCheck(id)) {
                System.out.println("중복된 ID입니다. 다시 입력해주세요");
                continue;
            }

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
        while (true) {
            System.out.println("사장님인가요 손님인가요?");
            System.out.println("1. 사장");
            System.out.println("2. 손님");
            System.out.print(" : ");

            int whatRole = sc.nextInt();
            sc.nextLine();

            // todo : Enum으로 리팩토링
            if (whatRole == 1) {
                thisRole = "사장님";
                break;
            } else if (whatRole == 2) {
                thisRole = "손님";
                break;
            } else {
                System.out.println("정확한 숫자를 입력해주세요.");
            }
        }

        User user = new User(thisId, thisPassword, thisRole);
        userlist.add(user);
//    FileWriter writer = new FileWriter("C:/Users/dmsck/OneDrive/바탕 화면/whale/coding_whale/250708_cafe_order_pjt/Users.txt", true);
        Path userFilePath = Constants.BASE_PATH.resolve("Users.txt");
        FileWriter writer = new FileWriter(userFilePath.toFile(), true);
        writer.write((user.getId()) + "," + user.getPassword() + "," + user.getRole() + "\n");
        writer.close();
        System.out.println("회원가입이 완료되었습니다.");
    }

    // id 중복 확인
    public Boolean idDuplicateCheck(String id) {
        boolean idchecker = true;

        // 중복 확인
        for (int i = 0; i < userlist.size(); i++) {
            if (id.equals(userlist.get(i).getId())) {
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

    // 로그인
    public User login() {
        System.out.println("[로그인]");

        while (true) {
            boolean loginSuccess = false;
            boolean idChecker = false;
            boolean pwChecker = false;

            System.out.print("id를 입력해주세요 : ");
            String id = sc.nextLine();

            System.out.print("password를 입력해주세요 : ");
            String password = sc.nextLine();

            if (userlist.isEmpty()) {
                System.out.println("유효하지 않은 id/pw입니다.");
                System.out.println("회원가입을 먼저 진행해주세요.");
            }

            User 찾은사용자 = findUser(id);


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

        }
    }

    // id로 객체 찾기
    public User findUser(String id) {
        for (int i = 0; i < userlist.size(); i++) {
            if (userlist.get(i).getId().equals(id)) {
                return userlist.get(i);
            }
        }
        return null;
    }

    // User 파일 load
    public void loadUserFile() throws IOException {
        Path userFilePath = Constants.BASE_PATH.resolve("Users.txt");
        BufferedReader reader = new BufferedReader((new FileReader(userFilePath.toFile())));


        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String id = parts[0];
            String password = parts[1];
            String role = parts[2];

            User user = new User(id, password, role);
            userlist.add(user);
        }

        reader.close();
    }

}

class Menu {
    private String menuName;
    private int menuPrice;
    private String menuOption;
    private String menuRecommend;


    public Menu() {
    }

    public Menu(String menuName, int menuPrice, String menuOption) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuOption = menuOption;
    }

    public Menu(String recomReason) {
        this.menuRecommend = recomReason;
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

    // Create
    public void menuCreate(String name, int price, String option) throws IOException {
        Menu menu = new Menu(name, price, option); // 메뉴 생성
        menus.add(menu);

        Path menuFilePath = Constants.BASE_PATH.resolve("Menus.txt");
        FileWriter writer = new FileWriter(menuFilePath.toFile(), true);
        writer.write(menu.getMenuName() + "," + menu.getMenuPrice() + "," + menu.getMenuOption() + "\n");
        writer.close();
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
                    System.out.println("  1. 커피류(coffee)\n" + "  2. 라떼류(latte)\n" + "  3. 차류(tea)");
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

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String name = parts[0];
            int price = Integer.parseInt(parts[1]);
            String option = parts[2];

            Menu menu = new Menu(name, price, option);
            menus.add(menu);
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
        MenuList menuList = new MenuList();
        OrderHistory orderHistory = new OrderHistory();
        MyMenu myMenu = new MyMenu();
        UserList userList = new UserList();
//        String basePath = UserList.BASE_PATH; // UserList 클래스의 주소 값
//        String basePath = userList.BASE_PATH; // UserList 객체/인스턴스의 주소 값


        Scanner sc = new Scanner(System.in);

        // 서비스 시작
        while (true) {
            System.out.println();
            System.out.println("안녕하세요, 카페 주문 서비스입니다.");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 프로그램 종료");
            System.out.print("메뉴를 선택해주세요 : ");
            int loginChoice = sc.nextInt();
            sc.nextLine();

            int role = 0;

            User 현재유저 = new User();

            if (loginChoice == 1) {
                userList.registerUser();
                continue;
            } else if (loginChoice == 2) {
                User 로그인한사용자 = userList.login();
                현재유저 = 로그인한사용자;
                if ("사장님".equals(로그인한사용자.getRole())) {
                    role = 1;
                } else if ("손님".equals(로그인한사용자.getRole())) {
                    role = 2;
                }

                // 로그아웃
            } else if (loginChoice == 3) {
                System.out.println("로그아웃이 되었습니다.");
                break;
            }


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
                    System.out.println("6. 로그아웃");
                    System.out.print("할 일을 선택해주세요 : ");

                    int menuSelect = sc.nextInt();
                    sc.nextLine();

                    // 1-1. 메뉴 등록 및 관리
                    if (menuSelect == 1) {
                        System.out.println();
                        System.out.println("[메뉴 등록 및 관리]");
                        System.out.println("1. 메뉴 등록");
                        System.out.println("2. 등록 메뉴 확인");
                        System.out.println("3. 메뉴 수정");
                        System.out.println("4. 메뉴 삭제");
                        System.out.println("5. 처음으로 돌아가기");
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

                        if (choice == 5) {
                            break;
                        }


                    }

                    // 1-2. 주문 내역 확인
                    else if (menuSelect == 2) {
                        orderHistory.OrderCheck();
                    }

                    // 1-3. 추천 메뉴 등록 및 관리
                    else if (menuSelect == 3) {
                        menuList.menuRecommend();
                    }

                    // 1-6. 로그아웃
                    if (menuSelect == 6) {
                        System.out.println("로그아웃이 완료되었습니다.");
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
            }


            // 3. 프로그램 종료
            if (role == 3) {
                break;
            }


        }
    }
}

