package net.codingme.box.leetcode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * <p>
 * 给定一个整数数组，返回两个数字的索引，使它们相加到特定目标。 您可以假设每个输入只有一个解决方案，并且您可能不会两次使用相同的元素。
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 * @Author niujinpeng
 * @Date 2018/10/31 18:12
 */
public class L001_TwoSum {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        //int[] ints = new TwoSum().twoSum(nums, target);
        int[] ints = new L001_TwoSum().twoSumByHash(nums, target);
        for (int anInt : ints) {
            System.out.println(anInt);

        }
    }

    /**
     * 暴力破解法
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((nums[i] + nums[j]) == target) {
                    int[] result = {i, j};
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * HashMap方法
     * 99.3% over
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSumByHash(int[] nums, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(target - nums[i])) {
                Integer value = hashMap.get(target - nums[i]);
                return new int[]{value, i};
            }
            hashMap.put(nums[i], i);
        }
        return null;
    }

}
