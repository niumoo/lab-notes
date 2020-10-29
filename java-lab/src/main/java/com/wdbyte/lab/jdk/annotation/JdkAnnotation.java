package com.wdbyte.lab.jdk.annotation;

/**
 * <p>
 * Java自带的几个注解
 *
 * @Author niujinpeng
 * @Date 2019/4/1 13:10
 */
public class JdkAnnotation {

    /**
     * 抑制警告
     */
    @SuppressWarnings(":deprecated")
    public void test() {
        deparecated();
    }

    /**
     * @Deprecated 废弃方法
     */
    @Deprecated
    public void deparecated() {
        System.out.println("废弃方法");
    }

    /**
     * 方法重写
     * 
     * @return
     */
    @Override
    public String toString() {
        return "JdkAnnotation{}";
    }
}
