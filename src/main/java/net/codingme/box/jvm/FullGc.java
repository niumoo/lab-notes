package net.codingme.box.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * JVM Options : -Xms10M -Xmx10M -Xmn5M -XX:+PrintGCDetails
 * 不断的 FullGc
 * 
 * @author niujinpeng
 * @Date 2020/7/6 16:45
 */
public class FullGc {

    /**
     * 内存占位符对象,一个placeholder大约占1MB
     */
    public byte[] placeholder = new byte[1024 * 1024];

    public static void main(String[] args) throws Exception {
        // 获取当前堆的大小 byte 单位
        List<FullGc> list = new ArrayList<FullGc>();
        while (true) {
            Thread.sleep(1000);
            long freeMemory = Runtime.getRuntime().freeMemory();
            System.out.println("当前堆剩余大小" + freeMemory);
            if (freeMemory < 1024 * 1024 * 5) {
                list.clear();
            }
            list.add(new FullGc());
        }

        // 方式2
        // while (true){
        // Thread.sleep(1000);
        // System.gc();
        // }
    }
}
