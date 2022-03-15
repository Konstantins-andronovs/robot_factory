package com.example.factoryback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class StockService {

    private static StockService singleInstance = null;
    private static LinkedBlockingQueue<StockOption> foos;
    private static LinkedBlockingQueue<StockOption> bars;
    private static LinkedBlockingQueue<StockOption> foobars;
    private static final AtomicInteger balance = new AtomicInteger();
    private static final AtomicInteger robots = new AtomicInteger(2);

    public static LinkedBlockingQueue<StockOption> getFoos() {
        return foos;
    }

    public static LinkedBlockingQueue<StockOption> getBars() {
        return bars;
    }

    public static LinkedBlockingQueue<StockOption> getFoobars() {
        return foobars;
    }

    public static AtomicInteger getBalance() {
        return balance;
    }

    public static AtomicInteger getRobots() {
        return robots;
    }

    private StockService() {

        foos = new LinkedBlockingQueue<>();
        bars = new LinkedBlockingQueue<>();
        foobars = new LinkedBlockingQueue<>();

        // stock.put(StockOptions.FOO, new ArrayList<StockOption>());
        // stock.put(StockOptions.BAR, new ArrayList<StockOption>());
        // stock.put(StockOptions.FOOBAR, new ArrayList<StockOption>());

    }

    public static StockService getInstance() {
        if (singleInstance == null)
            singleInstance = new StockService();

        return singleInstance;
    }

    public void mineFoo() {
        changeActivity();
        try {
            Thread.sleep(1000L);
            foos.put(new StockOption(StockOptions.FOO));
            printStock();
        } catch (Exception e) {
            System.out.println("Error mining Foo " + e);
        }
    }

    public void mineBar() {
        changeActivity();
        try {

            int max = 2000;
            int min = 500;
            int range = max - min + 1;
            long busy = (long) (Math.random() * range) + min;
            Thread.sleep(busy);

            bars.put(new StockOption(StockOptions.BAR));
            printStock();
        } catch (Exception e) {
            System.out.println("Error mining Bar " + e);
        }
    }

    public void assembly() {
        changeActivity();
        try {
            if (foos.peek() != null && bars.peek() != null) {
                var fooMaterial = foos.poll();
                var barMaterial = bars.poll();

                Thread.sleep(2000L);
                if (new Random().nextInt(10) < 6) {
                    System.out.println("Assembly succeded");
                    var components = List.of(fooMaterial,
                            barMaterial);
                    var foobar = new StockOption(StockOptions.FOOBAR, components);
                    foobars.put(foobar);

                } else {
                    System.out.println("Assembly failed");
                    fooMaterial = null;
                    foos.put(barMaterial);
                }
            } else {
                System.out.println("Assembly impossible, mising materials");
            }
            printStock();
        } catch (Exception e) {
            System.out.println("Assembly error, " + e);
        }
    }

    public void sell() {
        changeActivity();
        try {
            if (foobars.size() >= 5) {
                List<StockOption> foobarPocket = new ArrayList<StockOption>();
                foobars.drainTo(foobarPocket, 5);
                int currentBalance = balance.addAndGet(foobarPocket.size());
                System.out.println("Sold: " + foobarPocket.size() + " Current Balance: " + currentBalance);
                Thread.sleep(10000L);
                printStock();

            }
        } catch (InterruptedException e) {
            System.out.println("Thread sell error");
            e.printStackTrace();
        }

    }

    public void buyRobot() {
        changeActivity();
        if (balance.get() >= 3 && foos.size() >= 6) {

            List<StockOption> fooPocket = new ArrayList<StockOption>();

            foos.drainTo(fooPocket, 6);
            if (fooPocket.size() != 6) {
                for (StockOption option : fooPocket) {
                    try {
                        foos.put(option);
                    } catch (InterruptedException e) {
                        System.out.println("Error returning foo from pocket");
                        e.printStackTrace();
                    }
                }
                return;
            }

            int leftBalance = balance.addAndGet(-3);

            if (leftBalance >= 0) {
                robots.incrementAndGet();
                Robot r3 = new Robot();
                r3.start();

            } else {
                balance.addAndGet(3);
            }

            printStock();

        }
    }

    private void changeActivity() {
        try {
            Thread.sleep(5000L);
        } catch (Exception e) {
            System.out.println("Error changing activity " + e);
        }

    }

    private void printStock() {
        System.out.println("Current stock: " + foos.size() + "foos " + bars.size() + "bars " + foobars.size()
                + "foobars " + balance.get() + "eur " + robots.get() + "robots ");
    }
}
