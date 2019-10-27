package net.codingme.box.classloader;

import java.time.LocalTime;

/**
 * <p>
 * BaseManager 这个接口的子类要实现类的热加载功能。
 *
 * @Author niujinpeng
 * @Date 2019/10/24 23:30
 */
public class MyManager implements BaseManager {

    @Override
    public void logic() {
        System.out.println(LocalTime.now() + ": Java类的热加载 Oh~~~~ ");
    }
}
