package com.sd.ipc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {
    private int size;

    private String[] buffer;

    private Lock lock = new ReentrantLock();

    private Semaphore full;

    private Semaphore empty;

    private int inIndex;

    private int outIndex;

    public ProducerConsumer(int size) {
        this.size = size;
        buffer = new String[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = "";
        }
        full = new Semaphore(0);
        empty = new Semaphore(size);
    }

    public void produce(String message) {
        try {
            empty.acquire();
            lock.lock();
            buffer[inIndex] = message;
            inIndex = (inIndex + 1) % size;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            full.release();
        }
    }

    public String consume() {
        String get = null;
        try {
            full.acquire();
            lock.lock();
            get = buffer[outIndex];
            outIndex = (outIndex + 1) % size;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            empty.release();
        }
        return get;
    }
}
