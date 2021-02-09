package com.sd.ipc;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class PhilosopherDining {
    private int count;

    private Semaphore semaphore;

    private Semaphore[] chopsticks;

    public PhilosopherDining(int count) {
        this.count = count;
        // 最多允许 count - 1 个哲学家同时进餐
        semaphore = new Semaphore(count - 1);
        chopsticks = new Semaphore[count];
        for (int i = 0; i < count; i++) {
            chopsticks[i] = new Semaphore(1);
        }
    }

    /**
     * 第index个哲学家吃饭
     */
    public void eat(int index) {
        try {
            semaphore.acquire();
            chopsticks[index].acquire();
            chopsticks[(index + 1) % count].acquire();
            System.out.printf("%d start eating\n", index);
            Thread.sleep(new Random().nextInt(10000));
            System.out.printf("%d finish eating\n", index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            chopsticks[(index + 1) % count].release();
            chopsticks[index].release();
            semaphore.release();
        }
    }
}
