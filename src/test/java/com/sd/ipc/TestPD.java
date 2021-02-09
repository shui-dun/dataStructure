package com.sd.ipc;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestPD {
    public static void main(String[] args) {
        PhilosopherDining pd = new PhilosopherDining(5);
        ExecutorService pool = Executors.newCachedThreadPool();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int i = Integer.parseInt(scanner.nextLine());
            pool.submit(() -> {
                pd.eat(i);
            });
        }
    }
}
