package com.sd.ipc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderWriter {
    private Lock wMutex = new ReentrantLock();

    private Lock mutex = new ReentrantLock();

    private int readCount = 0;

    private String value;

    public ReaderWriter() {
    }

    public void write(String value) {
        wMutex.lock();
        try {
            this.value = value;
        } finally {
            wMutex.unlock();
        }
    }

    public String read() {
        String s;
        mutex.lock();
        ++readCount;
        if (readCount == 1) {
            wMutex.lock();
        }
        mutex.unlock();
        s = value;
        mutex.lock();
        --readCount;
        if (readCount == 0) {
            wMutex.unlock();
        }
        mutex.unlock();
        return s;
    }
}
