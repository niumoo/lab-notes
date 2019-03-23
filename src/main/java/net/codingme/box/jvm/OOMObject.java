package net.codingme.box.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于演示内存占用情况
 */
public class OOMObject {
    /**
     * 内存占位符对象,一个OOMObject大约占64KB
     */
    public byte[] placeholder = new byte[64 * 1024];

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            // 稍作延时,令监视曲线的变化更加明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args) throws Exception {
        // fillHeap(1000);

        // OutOfMemoryError
        // List<OOMObject> list = new ArrayList<OOMObject>();
        // while (true){
        // list.add(new OOMObject());
        // }
    }

}