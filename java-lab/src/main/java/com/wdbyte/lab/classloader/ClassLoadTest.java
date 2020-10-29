package com.wdbyte.lab.classloader;

/**
 * <p>
 *
 * 测试 Java 类的热加载
 * 
 * @Author niujinpeng
 * @Date 2019/10/24 23:55
 */
public class ClassLoadTest {

    public static void main(String[] args) {
        new Thread(new MsgHandle()).start();
    }
}
