import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        int 수용량 = 5; // 배열의 전체 크기

        String[] menuName = new String[수용량];
        int[] menuPrice = new int[수용량];
        String[] menuOption = new String[수용량];

        int 칸 = 0; // 현재 차있는 배열의 크기

        // 서비스 시작
        while (true) {
            System.out.println("안녕하세요, 카페 주문 서비스입니다. 역할을 선택해주세요.");
            System.out.println("1. 사장");
            System.out.println("2. 손님");
            System.out.println("3. 프로그램 종료");
            System.out.print("역할을 선택해주세요 : ");

            // 역할 선택
            Scanner sc = new Scanner(System.in);
            int role = sc.nextInt();
            sc.nextLine();

            // 임시
            System.out.println("선택한 역할 : " + role);
            System.out.println();



            // 1. 주인일 때
            if (role == 1) {
                while (true) {
                    System.out.println("안녕하세요 사장님,");
                    System.out.println("1. 메뉴 등록");
                    System.out.println("2. 등록 메뉴 확인");
                    System.out.println("3. 메뉴 수정");
                    System.out.println("4. 메뉴 삭제");
                    System.out.println("5. 역할 선택으로 돌아가기");

                    System.out.print("할 일을 선택해주세요 : ");

                    int choice = sc.nextInt();
                    sc.nextLine();
                    System.out.println();

                    // 1-1 메뉴 등록 (Create)
                    if (choice == 1) {
                        System.out.print("메뉴 이름을 작성해주세요 : ");
                        menuName[칸] = sc.nextLine();

                        System.out.print("메뉴의 가격을 입력해주세요 : ");
                        menuPrice[칸] = sc.nextInt();

                        sc.nextLine();

                        System.out.println("메뉴의 종류를 선택해주세요");
                        System.out.println("  1. 커피류(coffee)");
                        System.out.println("  2. 라떼류(latte)");
                        System.out.println("  3. 차류(tea)");
                        System.out.print(" : ");
                        menuOption[칸] = sc.nextLine();

                        System.out.println(칸);
                        칸 += 1;
                        System.out.println(칸);

                        System.out.println("등록이 완료되었습니다.");

                    }

                    // 1-2. 등록 메뉴 확인 (Read)
                    if (choice == 2) {
                        System.out.println("[현재 등록된 메뉴 목록]");
                        for (int i = 0; i <= 수용량; i++) {
                            System.out.println(menuName[i]);
                            System.out.println(menuPrice[i]);
                            System.out.println(menuOption[i]);
                        }

                    }


                    // 1-5. 프로그램 종료
                    if (choice == 5) {
                        System.out.println("프로그램을 종료합니다.");
                        break;
                    }

                }
            }


//            // 2. 손님일 때
//            if (role == 2) {
//                while (true) {
//                    System.out.println("안녕하세요 손님, 카페 주문 서비스입니다.");
//                    System.out.println("할 일을 선택해주세요.");
//                    System.out.println("1. 메뉴 선택");
//                    System.out.println("2. 오늘의 메뉴 추천");
//                    System.out.println("3. 찜한 메뉴");
//                    System.out.print(" : ");
//
//                    int cusChoice = sc.nextInt();
//                    sc.nextLine();
//
//                    // 2-1. 메뉴 선택
//                    if (cusChoice == 1) {
//                        // 메뉴 안내
//                        System.out.println("[전체 메뉴]");
//                        System.out.println("1. " + menuName + " 가격 : " + menuPrice);
//
//                        System.out.println();
//                        System.out.println("메뉴를 선택해주세요 : ");
//
//                        int finalMenu = sc.nextInt();
//                        sc.nextLine();
//                        int finalPrice = menuPrice;
//                        String finalOption = "";
//
//                        if (finalMenu == 1) {
//                            System.out.println("샷 옵션을 선택해주세요");
//                            System.out.println("1.기본(2샷) " + System.lineSeparator() +
//                                    "2.연하게(1샷)" + System.lineSeparator() +
//                                    "3.샷추가(3샷) + 500원" + System.lineSeparator() +
//                                    "4.디카페인 + 1000원");
//                            System.out.print(" : ");
//
//                            int choicePrice = sc.nextInt();
//                            sc.nextLine();
//
//                            if (choicePrice == 1) {
//                                finalOption = "기본(2샷)";
//                            } else if (choicePrice == 2) {
//                                finalOption = "연하게(1샷)";
//                            } else if (choicePrice == 3) {
//                                finalOption = "샷추가(3샷)";
//                                finalPrice += 500;
//                            } else if (choicePrice == 4) {
//                                finalOption = "디카페인";
//                                finalPrice += 1000;
//                            }
//
//
//                        }
//

//
//                    }
//                }
//
//
//            }


            // 3. 프로그램 종료
            if (role == 3) {
                break;
            }


        }
    }
}
