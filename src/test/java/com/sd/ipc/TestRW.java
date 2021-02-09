package com.sd.ipc;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestRW {
    public static void main(String[] args) {
        ReaderWriter rw = new ReaderWriter();
        ExecutorService pool = Executors.newCachedThreadPool();
        Scanner input = new Scanner(System.in);
        while (true) {
            String s = input.nextLine();
            if (s.equals("r")) {
                pool.submit(() -> System.out.printf("read %s\n", rw.read()));
            } else {
                pool.submit(() -> {
                    System.out.println("start writing");
                    rw.write(s);
                    System.out.println("finish writing");
                });
            }
        }
    }
}
