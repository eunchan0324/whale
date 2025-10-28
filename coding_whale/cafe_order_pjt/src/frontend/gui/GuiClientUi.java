package frontend.gui;

import backend.menu.*;
import backend.order.*;
import backend.store.*;
import backend.user.*;

import javax.swing.*;
import java.io.IOException;

/**
 * GUI 클아이언트 UI
 * Swing 기반 그래픽 사용자 인터페이스
 */
public class GuiClientUi {
    // 백엔드 객체 (의존성 주입, static으로 GUI 메서드들이 접근 가능)
    private static StoreList storeList;
    private static UserList userList;
    private static MenuStatusList menuStatusList;
    private static MenuList menuList;
    private static OrderList orderList;
    private static MyMenu myMenu;

    /**
     * 생성자 - 백엔드 객체 주입
     */
    public GuiClientUi(StoreList storeList, UserList userList, MenuStatusList menuStatusList, MenuList menuList, OrderList orderList, MyMenu myMenu) {
        GuiClientUi.storeList = storeList;
        GuiClientUi.userList = userList;
        GuiClientUi.menuStatusList = menuStatusList;
        GuiClientUi.menuList = menuList;
        GuiClientUi.orderList = orderList;
        GuiClientUi.myMenu = myMenu;
    }

    /**
     * GUI 모드 시작
     */
    public void start() {
        // TODO : showMainScreen() 호출 예정
        System.out.println("[GuiClientUi] start() 메서드 - 구현 예정");
    }

    // TODO : 기존 GUI 메서드들 이동 예정

}
