package multithread;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    private int size;

    private enum State {
        HUNGARY, EATING, THINKING
    }

    private State[] states;

    private Semaphore mutex = new Semaphore(1);

    private Random random = new Random();

    private Semaphore[] s;

    public DiningPhilosophers(int size) {
        this.size = size;
        states = new State[size];
        for (int i = 0; i < size; i++) {
            states[i] = State.THINKING;
        }
        s = new Semaphore[size];
        try {
            for (int i = 0; i < size; i++) {
                s[i] = new Semaphore(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Philosopher extends Thread {
        private int id;

        public Philosopher(int id) {
            this.id = id;
        }

        private void think() {
            try {
                sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void eat() {
            try {
                sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private int left(int i) {
            return (i - 1 + size) % size;
        }

        private int right(int i) {
            return (i + 1) % size;
        }

        private void takeForks() {
            try {
                mutex.acquire();
                if (states[left(id)] != DiningPhilosophers.State.EATING && states[right(id)] != DiningPhilosophers.State.EATING) {
                    states[id] = DiningPhilosophers.State.EATING;
                    mutex.release();
                } else {
                    states[id] = DiningPhilosophers.State.HUNGARY;
                    mutex.release();
                    s[id].acquire();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void putForks() {
            try {
                mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            states[id] = DiningPhilosophers.State.THINKING;
            int left = left(id);
            if (states[left] == DiningPhilosophers.State.HUNGARY && states[left(left)] != DiningPhilosophers.State.EATING) {
                states[left] = DiningPhilosophers.State.EATING;
                s[left].release();
            }
            int right = right(id);
            if (states[right] == DiningPhilosophers.State.HUNGARY && states[right(right)] != DiningPhilosophers.State.EATING) {
                states[right] = DiningPhilosophers.State.EATING;
                s[right].release();
            }
            mutex.release();
        }

        @Override
        public void run() {
            while (true) {
                think();
                takeForks();
                eat();
                putForks();
            }
        }
    }

    private class Print extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    sleep(500);
                    mutex.acquire();
                    for (int i = 0; i < size; i++) {
                        System.out.printf("%s:%d:%d\t", states[i].toString(), s[i].availablePermits(), s[i].getQueueLength());
                    }
                    System.out.println();
                    mutex.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void test() {
        Philosopher[] philosophers = new Philosopher[size];
        for (int i = 0; i < size; i++) {
            philosophers[i] = new Philosopher(i);
        }
        for (int i = 0; i < size; i++) {
            philosophers[i].start();
        }
        new Print().start();
    }
}
