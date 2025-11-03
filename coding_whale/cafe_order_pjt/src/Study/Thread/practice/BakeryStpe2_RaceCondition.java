package Study.Thread.practice;

public class BakeryStpe2_RaceCondition {
    static int dough = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable worker = () -> {
            for (int i = 0; i < 10_000; i++) {
                dough++;
            }
        };

        Thread workerA = new Thread(worker);
        Thread workerB = new Thread(worker);

        workerA.start();
        workerB.start();

        workerA.join();
        workerB.join();

        System.out.println("기대 반죽량 : 20000");
        System.out.println("실제 반죽량 : " + dough);
    }
}

