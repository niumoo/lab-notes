package com.wdbyte.lab.jvm;

public class DeadLock {

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        new Thread(() -> {
            System.out.print("thread1 starting....");
            synchronized (DeadLock.class) {
                System.out.println("Deadlock deadlock");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (Object.class) {
                    System.out.println("Deadlock object");
                }
            }
        }).start();

        new Thread(() -> {
            System.out.print("thread2 starting....");
            synchronized (Object.class) {
                System.out.println("Deadlock object");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (DeadLock.class) {
                    System.out.println("Deadlock deadlock");
                }
            }
        }).start();
    }

}