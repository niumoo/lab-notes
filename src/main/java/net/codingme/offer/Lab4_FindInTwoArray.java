package net.codingme.offer;

/**
 * <p>
 * 剑指office第四题
 * 
 * 在一个NXN的矩阵中查找是否存在某个数字，矩阵数据每一行从左向右从小到大，从上到下从小到大
 * 
 *
 * @Author niujinpeng
 * @Date 2019/3/22 13:36
 */
public class Lab4_FindInTwoArray {

    public static void main(String[] args) {
        solution(100);
    }

    public static boolean solution(int target) {
        // 初始化数组
        int[] line1 = {1, 2, 3, 4, 5};
        int[] line2 = {2, 3, 4, 5, 6};
        int[] line3 = {5, 6, 7, 8, 9};
        int[] line4 = {7, 8, 10, 14, 16};
        int[] line5 = {9, 11, 13, 15, 18};
        int[][] arr = {line1, line2, line3, line4, line5};

        int height = arr[0].length - 1;
        int width = 0;
        int value = arr[width][height];
        while (value != target) {
            if (value > target) {
                height--;
            } else {
                width++;
            }
            if (height < 0 || width == arr[0].length) {
                System.out.println("不存在这个数字");
                return false;
            }
            value = arr[width][height];
        }
        System.out.println("存在于[" + width + "][" + height + "]");
        return true;
    }
}
