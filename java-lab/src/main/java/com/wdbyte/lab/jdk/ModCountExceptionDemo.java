package com.wdbyte.lab.jdk;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Phaser;

import org.junit.Test;

/**
 * 并发修改异常的绕过方式
 *
 * @author github.com/niumoo
 * @date 2021/02/11
 */
public class ModCountExceptionDemo {

    @Test
    public void updateCollections() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "Hello", "World", "C++", "Java");

        list.listIterator();
        Iterator iterator = list.iterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        list.remove("C++");
        System.out.println(iterator.next());
    }

    @Test
    public void updateCollectionsFor() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "Hello", "World", "C++", "Java");

        list.listIterator();
        Iterator iterator = list.iterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        list.remove("C++");
        // 40 多亿次遍历，溢出到负数，继续溢出到原值
        for (int n = Integer.MIN_VALUE; n < Integer.MAX_VALUE; n++) { ((ArrayList)list).trimToSize(); }
        System.out.println(iterator.next());
    }

    @Test
    public void updateCollectionThread() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "Hello", "World", "C++", "Java");

        list.listIterator();
        Iterator iterator = list.iterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());

        // 开始操作
        list.set(2, "Java");
        Phaser phaser = new Phaser(2);
        Thread main = Thread.currentThread();
        new Thread(() -> {
            synchronized (System.out) {
                phaser.arriveAndDeregister();
                while (main.getState() != State.BLOCKED) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.remove(3);
            }
        }).start();
        phaser.arriveAndAwaitAdvance();

        System.out.println(iterator.next());

        // 输出集合
        System.out.println(list);

        /**
         * 得到输出
         *
         * Hello
         * World
         * Java
         * [Hello, World, Java]
         */
    }

    @Test
    public void updateCollectionsObject() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "Hello", "World", "C++", "Java");

        list.listIterator();
        Iterator iterator = list.iterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());

        // 开始操作
        ((List)list).set(2, new Object() {
            public String toString() {
                String s = list.get(3);
                list.remove(this);
                return s;
            }
        });

        System.out.println(iterator.next());
    }

}
