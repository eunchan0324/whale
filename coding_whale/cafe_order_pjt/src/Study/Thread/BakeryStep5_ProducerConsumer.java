package Study.Thread;

public class BakeryStep5_ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("생산중..." + i + "번째 빵");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        };

        // 빵 생산
        Thread baker = new Thread(runnable);

        // 빵 포장
        Thread packer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("포장중..." + i + "번째 빵");
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                }
            }
        } );

        baker.start();
        System.out.println("베이커 스레드 시작");
        packer.start();
        System.out.println("패커 스레드 시작");

        baker.join();
        packer.join();

        System.out.println("빵 생산 완료");

    }

}

