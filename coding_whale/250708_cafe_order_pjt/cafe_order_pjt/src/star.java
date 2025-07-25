public class star {
  public static void main(String[] args) {
    // 직각 삼각형 별찍기
//    for (int i=0; i<5; i++) {
//      for (int j=0; j<=i; j++) {
//        System.out.print("*");
//      }
//      System.out.println();
//    }


    // 마름모 별찍기
    for (int i=0; i<5; i++) {
      if (i == 0 || i == 4) {
        System.out.println("  *  ");
      } else if (i == 1 || i == 3) {
        System.out.println(" *** ");
      } else {
        System.out.println("*****");
      }
    }

//    System.out.println("  *  ");
//    System.out.println(" *** ");
//    System.out.println("*****");
//    System.out.println(" *** ");
//    System.out.println("  *  ");
  }
}
