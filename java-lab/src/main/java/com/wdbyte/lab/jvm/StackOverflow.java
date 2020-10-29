package com.wdbyte.lab.jvm;

/**
 * <p>
 *
 * @Author niujinpeng
 * @Date 2019/3/22 14:23
 */
public class StackOverflow {
    public static void main(String[] args) {
        System.out.println(count(0));
    }

    public static int count(int i) {
        System.out.println(i);
        return i + count(i + 1);
    }
}
