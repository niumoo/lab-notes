package net.codingme.box.algorithm.office;

/**
 * <p>
 * 剑指office第二题
 * Java 单例模式
 *
 * @Author niujinpeng
 * @Date 2019/3/21 22:22
 */
public class Lab2_Singleton {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getSingleton();
        Singleton2 singleton2 = Singleton2.getSingleton2();
    }
}

/**
 * 懒汉模式，延迟初始化
 */
class Singleton {
    private Singleton() {
    }

    private static class SingletonHolder {
        static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getSingleton() {
        return SingletonHolder.INSTANCE;
    }
}


/**
 * 饿汉模式，直接初始化
 */
class Singleton2 {
    private static Singleton2 singleton2 = new Singleton2();

    private Singleton2() {
    }

    public static Singleton2 getSingleton2() {
        return singleton2;
    }

}
