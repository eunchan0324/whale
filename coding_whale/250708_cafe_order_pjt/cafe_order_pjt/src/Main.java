import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    // 메뉴,가격 변수 저장
    String menu1 = "아메리카노";
    int price1 = 4000;

    String menu2 = "라떼";
    int price2 = 4500;

    String menu3 = "녹차";
    int price3 = 5000;

    // 온도 변수 저장
    String temp1 = "HOT";
    String temp2 = "ICE";
    String finalTemp = "";

    // 최종 메뉴, 옵션, 가격 저장
    String finalMenu = "";
    int finalPrice = 0;
    String finalOption = "";

    // 메뉴 선택 문구 출력
    System.out.println("안녕하세요 웨일 Cafe 입니다.");
    System.out.println("1. " + menu1 + " - " + price1 + "원");
    System.out.println("2. " + menu2 + " - " + price2 + "원");
    System.out.println("3. " + menu3 + " - " + price3 + "원");

    // 메뉴 입력 받기
    Scanner sc1 = new Scanner((System.in));
    System.out.print("원하는 메뉴의 번호를 입력하세요 : ");

    // 입력한 메뉴를 최종 메뉴, 가격 변수로 저장하기
    int menuInput = sc1.nextInt(); // 숫자

    if (menuInput == 1) {
      finalMenu = menu1;
      finalPrice = price1;

      System.out.println("샷 옵션을 선택해주세요");
      System.out.println("1.기본(2샷) " + System.lineSeparator() +
          "2.연하게(1샷)" +  System.lineSeparator() +
          "3.샷추가(3샷) + 500원" +   System.lineSeparator() +
          "4.디카페인 + 1000원");

      Scanner sc2 = new Scanner(System.in);
      System.out.print("원하는 옵션을 선택해주세요 : ");
      int optionInput = sc2.nextInt();
      if (optionInput == 1) {
        finalOption = "기본(2샷)";
      } else if (optionInput == 2) {
        finalOption = "연하게(1샷)";
      } else if (optionInput == 3) {
        finalOption = "샷추가(3샷)";
        finalPrice += 500;
      } else if (optionInput == 4) {
        finalOption = "디카페인";
        finalPrice += 1000;
      }


    } else if (menuInput == 2) {
      finalMenu = menu2;
      finalPrice = price2;

      System.out.println("우유 종류를 선택해주세요");
      System.out.println("1. 일반 우유" + System.lineSeparator() +
          "2. 두유 +500원" + System.lineSeparator() +
          "3. 무지방 우유 +300원");

      Scanner sc3 = new Scanner(System.in);
      System.out.print("원하는 옵션을 선택해주세요 : ");
      int optionInput = sc3.nextInt();
      if (optionInput == 1) {
        finalOption = "일반 우유";
      } else if (optionInput == 2) {
        finalOption = "두유";
        finalPrice += 500;
      } else if (optionInput == 3) {
        finalOption = "무지방 우유";
        finalPrice += 300;
      }


    } else if (menuInput == 3) {
      finalMenu = menu3;
      finalPrice = price3;

      System.out.println("밀크티로 변경하시겠습니까?");
      System.out.println("1. 아니요 (기본 녹차)" + System.lineSeparator() +
          "2. 네 (밀크티로 변경 + 500원)");

      Scanner sc4 = new Scanner(System.in);
      System.out.print("원하는 옵션을 선택해주세요 : ");
      int optionInput = sc4.nextInt();

      if (optionInput == 1) {
        finalOption = "기본 녹차";
      } else if (optionInput == 2) {
        finalOption = "녹차 밀크티";
        finalPrice += 500;
      }

    } else {
      System.out.println("메뉴에 존재하는 않는 번호입니다. 알맞는 메뉴를 입력해주세요. (ex:1~3)");
      System.out.println("프로그램을 종료합니다.");
      return;
    }

    System.out.println("온도를 선택해주세요");
    System.out.println("1. " + temp1);
    System.out.println("2. " + temp2);

    Scanner sc5 = new Scanner((System.in));
    System.out.print("원하는 온도를 선택해주세요 : ");
    int tempInput = sc5.nextInt();

    if (tempInput == 1) {
      finalTemp = temp1;
    } else if (tempInput == 2) {
      finalTemp = temp2;
    }


    // 최중 주문 출력
    System.out.println("[최종 주문]" +  System.lineSeparator() +
        "메뉴 : " + finalMenu + " - " + finalOption + System.lineSeparator() +
        "온도 : " + finalTemp + System.lineSeparator() +
        "최종 가격 : " +  finalPrice + "원 입니다.");

  }
}