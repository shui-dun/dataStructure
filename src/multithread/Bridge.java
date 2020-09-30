package multithread;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Bridge {
    private Semaphore mutex = new Semaphore(1);

    private Semaphore mutex1 = new Semaphore(1);

    private Semaphore mutex2 = new Semaphore(1);

    private Random random;

    private int n1 = 0;

    private int n2 = 0;

    private void crossBridge(String name, int direction) {
        String s = direction == 1 ? "->" : "<-";
        System.out.println(name + "开始" + s);
        try {
            Thread.sleep(Math.abs(random.nextLong()) % 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "结束" + s);
    }

    private class Direction1 extends Thread {
        private String name;

        public Direction1(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                mutex1.acquire();
                if (n1 == 0) {
                    mutex.acquire();
                }
                n1++;
                mutex1.release();
                crossBridge(name, 1);
                mutex1.acquire();
                n1--;
                if (n1 == 0) {
                    mutex.release();
                }
                mutex1.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Direction2 extends Thread {
        private String name;

        public Direction2(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                mutex2.acquire();
                if (n2 == 0) {
                    mutex.acquire();
                }
                n2++;
                mutex2.release();
                crossBridge(name, 2);
                mutex2.acquire();
                n2--;
                if (n2 == 0) {
                    mutex.release();
                }
                mutex2.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void test(Random random) throws InterruptedException {
        if (random != null) {
            this.random = random;
        } else {
            this.random = new Random();
        }
        new Direction1("haha").start();
        Thread.sleep(Math.abs(this.random.nextLong()) % 1000);
        new Direction1("xixi").start();
        Thread.sleep(Math.abs(this.random.nextLong()) % 1000);
        new Direction2("hehe").start();
        Thread.sleep(Math.abs(this.random.nextLong()) % 1000);
        new Direction1("nana").start();
        Thread.sleep(Math.abs(this.random.nextLong()) % 1000);
        new Direction2("shui").start();
        Thread.sleep(Math.abs(this.random.nextLong()) % 1000);
        new Direction2("dun").start();
        Thread.sleep(Math.abs(this.random.nextLong()) % 1000);
        new Direction1("hi").start();
    }
}
