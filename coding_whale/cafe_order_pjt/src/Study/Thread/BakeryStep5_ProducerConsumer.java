package Study.Thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BakeryStep5_ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        // 빵 5개까지만 올릴 수 있는 선반 (큐)
        BlockingQueue<String> shelf = new ArrayBlockingQueue<>(5);

        // 생산자 스레드 (빵 굽기)
        Thread baker = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    String bread = "빵" + i;
                    shelf.put(bread); // 선반이 꽉 차면 자동으로 기다림
                    System.out.println("생산: " + bread);
                    Thread.sleep(300);
                }
                shelf.put("DONE");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 소비자 스레드 (빵 포장/판매)
        Thread clerk = new Thread(() -> {
            try {
                while (true) {
                    String bread = shelf.take(); // 빵이 없으면 기다림
                    if (bread.equals("DONE")) {
                        System.out.println("모든 빵 판매 완료!");
                        break;
                    }
                    System.out.println("소비: " + bread);

                    Thread.sleep(500); // 손님에게 전달 시간
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        baker.start();
        clerk.start();

        baker.join();
        clerk.join();

        System.out.println("프로그램 종료");
    }
}

