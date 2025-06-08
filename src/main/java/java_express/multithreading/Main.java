package java_express.multithreading;

public class Main {
    public static volatile boolean flag = true;
    public static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Привет из потока!");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

//        thread.start();
//        thread.join();

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("a");
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("b");
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

//        a.start();
//        b.start();

        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                while (Main.flag) {
                    count++;
                }
            }
        });

//        c.start();

//        Thread.sleep(2000);
        Main.flag = false;

//        System.out.println(java_express.first_practice.Main.count);

        Thread c1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) Counter.increment();
            }
        });

        Thread c2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) Counter.increment();
            }
        });

        c1.start();
        c2.start();

        c1.join();
        c2.join();

        System.out.println(Counter.count);

    }
}

class Counter {
    public static volatile int count = 0;

    public static synchronized void increment() {
        Counter.count++;
    }
}
