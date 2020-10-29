package com.wdbyte.offer;

/**
 * <p>
 * 剑指office第三题
 * <p>
 * 在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。数组中某些数字是重复的，
 * <p>
 * 但不知道有几个数字是重复的，也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
 *
 * @Author niujinpeng
 * @Date 2019/3/8 17:53
 */
public class Lab3_FindRepeatNumber {
    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 0, 2, 5};
        solution(arr);
    }

    /**
     * 把数字放到值对应的坐标上，如果坐标上已经存在相同值。说明存在重复
     *
     * @param arr
     */
    public static void solution(int[] arr) {
        if (arr == null || arr.length < 1) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            // 当前值
            while (arr[i] != i) {
                int value = arr[i];
                if (arr[value] == value) {
                    System.out.println("发现重复值" + value);
                    return;
                } else {
                    // swap
                    int temp = arr[value];
                    arr[value] = value;
                    arr[i] = temp;
                }
            }
        }
    }
}
