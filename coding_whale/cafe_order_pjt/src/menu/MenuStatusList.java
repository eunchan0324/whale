package menu;

import constant.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class MenuStatusList {
    ArrayList<MenuStatus> menuStatuses = new ArrayList<>();

    // Menu_status.txt save
    public void saveMenuStatusFile() throws IOException {
        Path menuFilePath = Constants.BASE_PATH.resolve("Menu_status.txt");
        FileWriter writer = new FileWriter(menuFilePath.toFile());

        for (int i = 0; i < menuStatuses.size(); i++) {
            MenuStatus status = menuStatuses.get(i);
            writer.write(status.getStoreId() + "," +
                    status.getMenuId() + "," +
                    status.getStatus() + "," +
                    status.getStock() + "\n");
        }
        writer.close();
    }

    // Menu_status.txt load
    public void loadMenuStatusFile() throws IOException {
        Path menuFilePath = Constants.BASE_PATH.resolve("Menu_status.txt");
        BufferedReader reader = new BufferedReader(new FileReader(menuFilePath.toFile()));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int storeId = Integer.parseInt(parts[0]);
            int menuId = Integer.parseInt(parts[1]);
            MenuSaleStatus status = MenuSaleStatus.valueOf(parts[2]);
            int stock = Integer.parseInt(parts[3]);

            MenuStatus menuStatus = new MenuStatus(menuId, storeId, status, stock);
            menuStatuses.add(menuStatus);
        }
        reader.close();
    }

    // 생성자 - loadMenuStatusFile()
    public MenuStatusList() throws IOException {
        loadMenuStatusFile();
    }

    // 판매자 id로 menu.MenuStatus 객체 반환
    public MenuStatus findMenuStatus(int storeId, int menuId) {
        for (int i = 0; i < menuStatuses.size(); i++) {
            if (menuStatuses.get(i).getStoreId() == storeId && menuStatuses.get(i).getMenuId() == menuId) {
                return menuStatuses.get(i);
            }
        }
        return null;
    }

    // 재고 수량 변경
    public void updateStock(int storeId, int menuId, int newStock) throws IOException {

        MenuStatus menuStatus = findMenuStatus(storeId, menuId);

        // 재고 정보가 있으면 : 기본 재고 정보 수정(update)
        if (menuStatus != null) {
            menuStatus.setStock(newStock);
            System.out.println("재고가 성공적으로 업데이트되었습니다.");
        }

        // 재고 정보가 없으면 :
        else {
            MenuStatus newMenuStatus = new MenuStatus(storeId, menuId, MenuSaleStatus.AVAILABLE, newStock);
            menuStatuses.add(newMenuStatus);
            System.out.println("새로운 메뉴의 재고가 등록되었습니다.");
        }

        saveMenuStatusFile(); // 파일 저장

    }

    // 판매 상태 변경
    public void updateStatus(int storeId, int menuId, MenuSaleStatus newStatus) throws IOException {
        MenuStatus menuStatus = findMenuStatus(storeId, menuId);

        // 판매 상태가 있다면 :
        if (menuStatus != null) {
            menuStatus.setStatus(newStatus);
            System.out.println("메뉴 상태가 " + newStatus.name() + "(으)로 변경되었습니다.");
        }

        // 판매 상태가 없다면 (null) :
        else {
            MenuStatus newMenuStatus = new MenuStatus(storeId, menuId, newStatus, 0); // 기본 재고 0으로 등록
            menuStatuses.add((newMenuStatus));
            System.out.println("새로운 메뉴의 상태가 " + newStatus.name() + "(으)로 등록되었습니다.");
        }

        saveMenuStatusFile(); // 파일 저장
    }


    /**
     * 지정된 메뉴의 재고를 1감소 시키는 메서드
     * 이 메서드를 호출하기 전에는 반드시 isAvailable()로 재고를 확인해야 함
     *
     * @param storeId 판매자 ID
     * @param menuId  메뉴 ID
     * @return 성공하면 ture, 메뉴를 찾지 못하면 false
     * @throws IOException
     */
    public boolean decreaseStock(int storeId, int menuId) throws IOException {

        MenuStatus menuStatus = findMenuStatus(storeId, menuId);

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
    public boolean isAvailable(int storeId, int menuId) {
        MenuStatus menuStatus = findMenuStatus(storeId, menuId);

        if (menuStatus == null) {
            return false;
        }

        // 조건식이 true 또는 false를 반환
        return menuStatus.getStock() > 0 && menuStatus.getStatus() == MenuSaleStatus.AVAILABLE;

    }

    // 판매 메뉴 등록
    public void registerMenuForSale(int storeId, int menuId) throws IOException {
        // 1. 이미 등록된 메뉴인지 먼저 확인
        if (findMenuStatus(storeId, menuId) != null) {
            System.out.println("이미 판매 목록에 등록된 메뉴입니다.");
            return;
        }

        // 2. 등록되지 않은 메뉴라면, 새로운 menu.MenuStatus 객체 생성
        // 초기 재고는 0개, 판매 상태는 AVAILABLE
        MenuStatus newMenuStatus = new MenuStatus(menuId, storeId, MenuSaleStatus.AVAILABLE, 0);

        // 3. 리스트에 추가
        menuStatuses.add(newMenuStatus);

        // 4. 파일에 저장
        saveMenuStatusFile();
        System.out.println("성공적으로 판매 메뉴에 등록되었습니다. 재고 관리를 통해 수량을 조절해주세요.");
    }

    // 판매 메뉴 삭제
    public void removeMenuForSale(int storeId, int menuId) throws IOException {
        MenuStatus targetMenuState = findMenuStatus(storeId, menuId);

        if (targetMenuState == null) {
            System.out.println("존재하지 않는 메뉴 ID입니다. 다시 입력해주세요");
            return;
        }

        menuStatuses.remove(targetMenuState);

        saveMenuStatusFile();
        System.out.println("성공적으로 판매 메뉴가 삭제되었습니다.");
    }

}
