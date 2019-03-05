package net.codingme.box.lab;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *
 * @Author niujinpeng
 * @Date 2018/11/28 15:15
 */
public class FindNumber {

    public static void main(String[] args) {
        long star = System.currentTimeMillis();
        int length = 400000000;
        byte[] bytes = new byte[length];
        Set<Integer> hashset = new HashSet<>(100);
        for (int i = 0; i < length; i++) {
            bytes[i] = 1;
        }
        System.out.println(bytes[65893696]);
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star) + "ms");
    }


    public void hashMapTest() {
        long star = System.currentTimeMillis();
        Set<Integer> hashset = new HashSet<>(100);
        for (int i = 0; i < 10000000; i++) {
            hashset.add(i);
        }
        System.out.println(hashset.contains(9999));
        //System.out.println(hashset.contains(2));
        //System.out.println(hashset.contains(3));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star) + "ms");
    }

    public void bitTest() {
        long star = System.currentTimeMillis();
        int length = 400000000;
        byte[] bytes = new byte[length];
        Set<Integer> hashset = new HashSet<>(100);
        for (int i = 0; i < length; i++) {
            bytes[i] = 1;
        }
        System.out.println(bytes[65893696]);
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star) + "ms");
    }
}
