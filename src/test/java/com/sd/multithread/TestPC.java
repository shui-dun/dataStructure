package com.sd.multithread;


import com.sd.multithread.ProducerConsumer;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestPC {
    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 3;
        ProducerConsumer pc = new ProducerConsumer(bufferSize);
        ExecutorService pool = Executors.newCachedThreadPool();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.nextLine();
            if (s.equals("con")) {
                pool.submit(() -> {
                    System.out.println("consume " + pc.consume());
                });
            } else {
                pool.submit(() -> {
                    pc.produce(s);
                    System.out.println("produce " + s);
                });
            }
        }
    }
}
