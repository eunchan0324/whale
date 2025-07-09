import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    // 1. 변수로 메뉴와 가격 저장
    String menu1 = "아메리카노";
    int price1 = 4000;

    String menu2 = "라떼";
    int price2 = 4500;

    String menu3 = "녹차";
    int price3 = 5000;

    // 2. 메뉴 선택 문구 출력
    System.out.println("안녕하세요 웨일 Cafe 입니다.");
    System.out.println("1. " + menu1);
    System.out.println("2. " + menu2);
    System.out.println("3. " + menu3);

    // 3. 사용자 입력 받기
    Scanner sc = new Scanner((System.in));
    System.out.print("원하는 메뉴의 번호를 입력하세요 : ");
    int menuInput = sc.nextInt();

    // 4. 입력에 맞는 출력하기
    if (menuInput == 1) {
      System.out.println(menu1 + "는 " + price1 + "입니다.");
    } else if (menuInput == 2) {
      System.out.println(menu2 + "는 " + price2 + "입니다.");
    } else if (menuInput == 3) {
      System.out.println(menu3 + "는 " + price3 + "입니다.");
    } else {
      System.out.println("메뉴에 존재하는 번호를 입력해주세요 (ex:1~3)");
    }
  }
}