package multithread;

import java.util.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {
    private int bufferSize;

    private Queue<Integer> goods;

    private Semaphore mutex = new Semaphore(1);

    private Semaphore full = new Semaphore(0);

    private Semaphore empty;

    public ProducerConsumer(int bufferSize) {
        this.bufferSize = bufferSize;
        goods = new ArrayDeque<>(bufferSize);
        empty = new Semaphore(bufferSize);
    }

    private class Producer extends Thread {
        private Random random = new Random();

        private int produce() {
            int newGoods = random.nextInt();
            try {
                sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return newGoods;
        }

        @Override
        public void run() {
            while (true) {
                int n = produce();
                try {
                    empty.acquire();
                    mutex.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                goods.offer(n);
                System.out.println(String.format("%d\t加入了缓存区", n));
                mutex.release();
                full.release();
            }
        }
    }

    private class Consumer extends Thread {
        private Random random = new Random();

        private void consume(int n) {
            try {
                sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    full.acquire();
                    mutex.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int n = goods.poll();
                System.out.println(String.format("%d\t移出了缓存区", n));
                mutex.release();
                empty.release();
                consume(n);
            }
        }
    }

    public void test() {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        producer.start();
        consumer.start();
    }
}
