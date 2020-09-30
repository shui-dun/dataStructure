package multithread;

import sun.awt.Mutex;

import java.util.Random;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class ReaderWriter {
    private int buffer = 0;

    private int readerSum;

    private Semaphore semaphore = new Semaphore(1);

    private int readerNum = 0;

    private Mutex mutex = new Mutex();

    private Random random = new Random();

    public ReaderWriter(int readerSum) {
        this.readerSum = readerSum;
    }

    private class Reader extends Thread {
        private int id;

        public Reader(int id) {
            this.id = id;
        }

        private void read() {
            try {
                sleep(random.nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%d读了%d\n", id, buffer);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    idle();
                    mutex.lock();
                    if (readerNum == 0) {
                        semaphore.acquire();
                    }
                    readerNum++;
                    mutex.unlock();
                    read();
                    mutex.lock();
                    readerNum--;
                    if (readerNum == 0) {
                        semaphore.release();
                    }
                    mutex.unlock();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void idle() {
        try {
            sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Writer extends Thread {
        private void write() {
            try {
                sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            buffer = random.nextInt();
            System.out.println("写入" + buffer);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    idle();
                    semaphore.acquire();
                    write();
                    semaphore.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void test() {
        Reader[] readers = new Reader[readerSum];
        for (int i = 0; i < readerSum; i++) {
            readers[i] = new Reader(i);
        }
        Writer writer = new Writer();
        writer.start();
        for (int i = 0; i < readerSum; i++) {
            readers[i].start();
        }
    }
}
