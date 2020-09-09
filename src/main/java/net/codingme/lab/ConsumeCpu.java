package net.codingme.lab;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>
 * 消耗CPU的程序
 *
 * @Author niujinpeng
 * @Date 2019/3/20 18:02
 */
public class ConsumeCpu {

    public static void main(String[] args) {
        while (true) {
            int[] arr = new int[10000000];
            for (int i = 0; i < 10000000; i++) {
                arr[i] = new Random().nextInt(10000000);
            }
            System.out.println("生成完成");
            Arrays.sort(arr);
            System.out.println("排序完成");
        }
    }
}
