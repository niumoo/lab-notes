package net.codingme.offer;

/**
 * <p>
 * 两个已经排序的数组，合并成一个数组，合并后保证依旧有序。
 *
 * @author niujinpeng
 * @Date 2020/6/24 16:07
 */
public class ArrayInsertSort {

    public static void main(String[] args) {
        int[] arr1 = {1, 3, 5, 7, 9};
        int[] arr2 = {2, 4, 6, 8, 10};
        int[] result = sortTwoArray(arr1, arr2);
        for (int i : result) {
            System.out.println(i);
        }

    }

    public static int[] sortTwoArray(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        int arr1Index = arr1.length - 1;
        int arr2Index = arr2.length - 1;

        for (int i = 0; i < result.length; i++) {
            if (arr1Index < 0) {
                result[result.length - 1 - i] = arr2[arr2Index];
                continue;
            }
            if (arr2Index < 0) {
                result[result.length - 1 - i] = arr1[arr1Index];
                continue;
            }
            if (arr1[arr1Index] > arr2[arr2Index]) {
                result[result.length - 1 - i] = arr1[arr1Index];
                arr1Index--;
            } else {
                result[result.length - 1 - i] = arr2[arr2Index];
                arr2Index--;
            }
        }
        return result;
    }

}
