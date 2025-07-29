import java.util.Scanner;

public class star {
  public static void main(String[] args) {
    // 오른쪽 직각 삼각형 별찍기
//    하드 코딩 : 가로 3 세로 4
//    System.out.println("*");
//    System.out.println("****");
//    System.out.println("*******");
//    System.out.println("**********");

    // 반복문
//    Scanner sc = new Scanner(System.in);
//    System.out.print("직각 삼각형의 오른쪽 가로로 늘어날 길이를 입력하세요 : ");
//    int widthAdd = sc.nextInt();
//    System.out.print("직각 삼각형의 세로 길이를 입력하세요 : ");
//    int length = sc.nextInt();


//    int totalWidth = 1;
//    for (int i = 1; i <= length; i++) {
//      for (int j = 1; j<= totalWidth; j++) {
//        System.out.print("*");
//      }
//      System.out.println();
//      totalWidth += widthAdd;
//    }


//    // 왼쪽 직각 삼각형 별찍기
    // 하드코딩 : 가로 3 세로 4
//    System.out.println("         *");
//    System.out.println("      ****");
//    System.out.println("   *******");
//    System.out.println("**********");

    // 반복문
//    Scanner sc = new Scanner(System.in);
//    System.out.print("직각 삼각형의 왼쪽 가로로 늘어날 길이를 입력하세요 : ");
//    int widthAdd = sc.nextInt();
//    System.out.print("직각 삼각형의 세로 길이를 입력하세요 : ");
//    int length = sc.nextInt();


//    int totalGap = (length - 1) * widthAdd;
//    int totalStar = 1;
//    for (int i = 1; i <= length; i++) {
//      for (int j = 1; j<= totalGap; j++) {
//        System.out.print(" ");
//      }
//      totalGap -= widthAdd;
//      for (int k = 1; k<= totalStar; k++) {
//        System.out.print("*");
//      }
//      totalStar += widthAdd;
//      System.out.println();
//    }


    // 직각 삼각형 합치기 = 이등변 삼각형
//    // 하드코딩 가로변화 2 세로변화 4
//    System.out.println("   *   ");
//    System.out.println("  ***  ");
//    System.out.println(" ***** ");
//    System.out.println("*******");

    // 반복문
//    Scanner sc = new Scanner(System.in);
//    System.out.print("삼각형의 가로로 늘어날 길이를 입력하세요 (짝수만) : ");
//    int widthAdd = sc.nextInt();
//    System.out.print("직각 삼각형의 세로 길이를 입력하세요 : ");
//    int length = sc.nextInt();
//
//    // 최종 가로
//    int totalWidth = (length - 1) * widthAdd + 1;
//    // 중앙 별 위치
//    int center = totalWidth / 2 + 1;
//    // 왼쪽 삼각형 공백
//    int totalGap = center - 1;
//    // 초기 중앙 별
//    int totalStar = 1;
//    // 오른쪽 늘어나는 가로 길이
//    int rightAddWidth = 0;
//
//    for (int i = 1; i <= length; i++) {
//      for (int j = 1; j <= totalGap; j++) {
//        System.out.print(" ");
//      }
//      for (int k = 1; k <= totalStar; k++) {
//        System.out.print("*");
//      }
//      for (int l = 1; l <= rightAddWidth; l++) {
//        System.out.print("*");
//      }
//      totalGap -= widthAdd / 2;
//      totalStar += widthAdd / 2;
//      rightAddWidth += widthAdd / 2;
//      System.out.println();
//    }


    // 이등변 삼각형 뒤집기
    // 반복문
    Scanner sc = new Scanner(System.in);
    System.out.print("삼각형의 가로로 늘어날 길이를 입력하세요 (짝수만) : ");
    int widthAdd = sc.nextInt();
    System.out.print("직각 삼각형의 세로 길이를 입력하세요 : ");
    int length = sc.nextInt();


    // 최종 가로
    int totalWidth = (length - 1) * widthAdd + 1;
    // 중앙 별 위치
    int center = totalWidth / 2 + 1;
    // 왼쪽 삼각형 공백
    int totalGap = 0;
    // 초기 별
    int totalStar = totalWidth / 2 + 1;
    // 오른쪽 늘어나는 가로 길이
    int rightAddWidth = totalWidth / 2;

    for (int i = 1; i <= length; i++) {
      for (int j = 1; j <= totalGap; j++) {
        System.out.print(" ");
      }
      for (int k = 1; k <= totalStar; k++) {
        System.out.print("*");
      }
      for (int l = 1; l <= rightAddWidth; l++) {
        System.out.print("*");
      }
      totalGap += widthAdd / 2;
      totalStar -= widthAdd / 2;
      rightAddWidth -= widthAdd / 2;
      System.out.println();
    }


    // 마름모 별찍기 // 가로 , 세로로 바꾸기
//        Scanner sc = new Scanner(System.in);
//        System.out.print("마름모의 길이를 입력하세요 (홀수, 최소 3이상) : ");
//        int 세로 = sc.nextInt();
//        int 가로 = sc.nextInt();
//
//        for (int i = 1; i <= 세로; i++) {
//            if (세로 % i == 0) {
//                System.out.println("  *  ");
//            } else if (세로 % i == 1) {
//                System.out.println(" *** ");
//            } else if (세로 % i == 세로 / 2) {
//                for (int j = 1; j <= 가로; j++) {
//                    System.out.print("*");
//                }
//                System.out.println();
//            }
//        }

    // 가로 3, 세로 3
//        System.out.println("  *  ");
//        System.out.println("* * *");
//        System.out.println("  *  ");
//
//        // 가로(별의 개수) 5, 세로 5
//        // 추가된 가로만큼 첫 줄에 공백이 생김 > 영향을 줌 > 변수에 반영
//        System.out.println("    *    ");
//        System.out.println("  * * *  ");
//        System.out.println("* * * * *");
//        System.out.println("  * * *  ");
//        System.out.println("    *    ");


//        for (int i = 0; i < 5; i++) {
//            if (i == 0 || i == 4) {
//                System.out.println("  *  ");
//            } else if (i == 1 || i == 3) {
//                System.out.println(" *** ");
//            } else {
//                System.out.println("*****");
//            }
//        }

    // 원 별찍기
//        System.out.println("      ***      ");
//        System.out.println("    *******      ");
//        System.out.println("  ***********      ");
//        System.out.println("***************      ");
//        System.out.println("***************      ");
//        System.out.println("  ***********      ");
//        System.out.println("    *******      ");
//        System.out.println("      ***      ");
//
//
//
//
//        // 별 별찍기 (오각형)
//        System.out.println("      *      ");
//        System.out.println("     * *     ");
//        System.out.println("  **     **      ");
//        System.out.println("    * * *      ");
//        System.out.println("   *     *      ");


  }
}
