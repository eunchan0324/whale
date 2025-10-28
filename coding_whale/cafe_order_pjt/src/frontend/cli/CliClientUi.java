package frontend.cli;

import backend.menu.*;
import backend.order.*;
import backend.store.*;
import backend.user.*;

/**
 * CLI 클라이언트 UI
 * 콘솔 기반 사용자 인터페이스
 */
public class CliClientUi {
    // 백엔드 객체 (의존성 주입)
    private StoreList storeList;
    private UserList userList;
    private MenuStatusList menuStatusList;
    private MenuList menuList;
    private OrderList orderList;
    private MyMenu myMenu;

    /**
     * 생성자 - 백엔드 객체 주입
     */
    public CliClientUi(StoreList storeList, UserList userList, MenuStatusList menuStatusList, MenuList menuList, OrderList orderList, MyMenu myMenu) {
        this.storeList = storeList;
        this.userList = userList;
        this.menuStatusList = menuStatusList;
        this.menuList = menuList;
        this.orderList = orderList;
        this.myMenu = myMenu;
    }

    /**
     * CLI 모드 시작
     */
    public void start() {
        // TODO : startCliMode() 메서드 내용을 여기로 이동 예정
        System.out.println("[CliClientUI] start() 메서드 - 구현 예정");
    }
}
