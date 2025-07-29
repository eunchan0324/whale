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
//
//
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
//        Scanner sc = new Scanner(System.in);
//        System.out.print("직각 삼각형의 왼쪽 가로로 늘어날 길이를 입력하세요 : ");
//        int widthAdd = sc.nextInt();
//        System.out.print("직각 삼각형의 세로 길이를 입력하세요 : ");
//        int length = sc.nextInt();
//
//
//        int totalGap = (length - 1) * widthAdd;
//        int totalStar = 1;
//        for (int i = 1; i <= length; i++) {
//            for (int j = 1; j <= totalGap; j++) {
//                System.out.print(" ");
//            }
//            totalGap -= widthAdd;
//            for (int k = 1; k <= totalStar; k++) {
//                System.out.print("*");
//            }
//            totalStar += widthAdd;
//            System.out.println();
//        }


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
//    Scanner sc = new Scanner(System.in);
//    System.out.print("삼각형의 가로로 변화 할 길이를 입력하세요 (짝수만) : ");
//    int widthAdd = sc.nextInt();
//    System.out.print("직각 삼각형의 세로 길이를 입력하세요 : ");
//    int length = sc.nextInt();
//
//
//    // 최종 가로
//    int totalWidth = (length - 1) * widthAdd + 1;
//    // 중앙 별 위치
//    int center = totalWidth / 2 + 1;
//    // 왼쪽 삼각형 공백
//    int totalGap = 0;
//    // 초기 별
//    int totalStar = totalWidth / 2 + 1;
//    // 오른쪽 늘어나는 가로 길이
//    int rightAddWidth = totalWidth / 2;
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
//      totalGap += widthAdd / 2;
//      totalStar -= widthAdd / 2;
//      rightAddWidth -= widthAdd / 2;
//      System.out.println();
//    }


        // 마름모 별찍기
        // 반복문
        Scanner sc = new Scanner(System.in);
        System.out.print("마름모의 가로로 변화 할 길이를 입력하세요 (짝수만) : ");
        int col = sc.nextInt();
        System.out.print("직각 삼각형의 세로 길이를 입력하세요(홀수만) : ");
        int length = sc.nextInt();

        // 현재 행 상태
        int cntCol = 1;
        // 마름모의 중앙
        int middleLine = length / 2 + 1;
        // 최대 가로 길이
        int middleWidth = (length / 2) * col + 1;
        // 중앙 별 위치
        int center = middleWidth / 2 + 1;
        // 왼쪽 삼각형 공백
        int totalGap = middleWidth - center;
        // 왼쪽 삼각형 별 개수 (초기 중앙)
        int totalStar = 1;
        // 오른쪽 별 개수
        int rightAddStar = 0;

        for (int i = 1; i <= length; i++) {
            // 가로 별찍기 코드
            for (int j = 1; j <= totalGap; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= totalStar; j++) {
                System.out.print("*");
            }
            for (int j = 1; j <= rightAddStar; j++) {
                System.out.print("*");
            }
            // 행 기준 작업 코드
            if (cntCol < middleLine) {
                totalGap -= col / 2;
                totalStar += col / 2;
                rightAddStar += col / 2;
                System.out.println();
            } else {
                totalGap += col / 2;
                totalStar -= col / 2;
                rightAddStar -= col / 2;
                System.out.println();
            }
            cntCol += 1;
        }


        // 원 별찍기
//        System.out.println("            * * *      ");
//        System.out.println("        * * * * * * *      ");
//        System.out.println("    * * * * * * * * * * *    ");
//        System.out.println("* * * * * * * * * * * * * * *");
//        System.out.println("* * * * * * * * * * * * * * *");
//        System.out.println("    * * * * * * * * * * *    ");
//        System.out.println("        * * * * * * *      ");
//        System.out.println("            * * *      ");

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
