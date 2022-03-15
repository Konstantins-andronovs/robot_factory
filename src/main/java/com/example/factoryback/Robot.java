package com.example.factoryback;

import java.util.Random;
import java.util.UUID;

public class Robot implements Runnable {

    private Thread t;
    private String threadName;
    private StockService stock;

    Robot() {
        threadName = UUID.randomUUID().toString();
        stock = StockService.getInstance();
        System.out.println("Creating " + threadName);
    }

    public void run() {

        while (StockService.getRobots().get() < 30) {

            System.out.println("Running " + threadName);

            if (StockService.getFoobars().size() >= 5) {
                System.out.println("Trying to sell " + threadName);
                stock.sell();
            } else if (StockService.getBalance().get() >= 3 && StockService.getFoos().size() >= 6) {
                System.out.println("Trying to buy robot " + threadName);
                stock.buyRobot();
            } else if (StockService.getBars().size() > 0 && StockService.getFoos().size() > 6) {
                System.out.println("Trying to assembly " + threadName);
                stock.assembly();
            } else {
                if (new Random().nextInt(10) < 7
                        && StockService.getFoos().size() - StockService.getBars().size() < 7) {
                    System.out.println("Mining Foo " + threadName);
                    stock.mineFoo();
                } else {
                    System.out.println("Mining Bar " + threadName);
                    stock.mineBar();
                }
            }
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}