package Study.Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BakeryStep6_Executor {
    public static void main(String[] args) throws InterruptedException {
        // 스레드 3개짜리 스레드 풀 생성
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // 5개의 작업 (빵 굽기) 제출
        for (int i = 1; i <= 5; i++) {
            int id = i;
            Runnable runnable = () -> {
                System.out.println("빵" + id + " 굽는 중..." + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                System.out.println("빵" + id + " 완성!");
            };
            pool.submit(runnable);
        }

        // 더 이상 새로운 작업은 받지 않음
        pool.shutdown();

        // 모든 작업이 끝날 때까지 기다림
        pool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("모든 작업 종료!");
    }
}
