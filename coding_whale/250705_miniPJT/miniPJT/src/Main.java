import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // 요일 배열 생성 후, 각 요소는 해야 할 일 요소를 가지고 있다.

        // 오늘의 요일 구하기
        LocalDate now = LocalDate.now();  // 현재 날짜 구하기
        String engToday = now.getDayOfWeek().toString(); // 날짜를 요일로 변수에 저장하기

        // 영어로 된 요일을 한글로 변경하여 새로운 변수에 저장하기
        String korToday = "";
        switch (engToday) {
            case "SUNDAY": korToday = "일요일";
            break;
            case "MONDAY": korToday = "월요일";
            break;
            case "TUESDAY": korToday = "화요일";
            break;
            case "WEDNESDAY": korToday = "수요일";
            break;
            case "THURSDAY": korToday = "목요일";
            break;
            case "FRIDAY": korToday = "금요일";
            break;
            case "SATURDAY": korToday = "토요일";
            break;
        }

        // 오늘의 날짜 출력하기
        System.out.println("오늘은 " + korToday + "입니다.");

    }
}