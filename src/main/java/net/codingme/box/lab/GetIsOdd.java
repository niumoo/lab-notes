package net.codingme.box.lab;

/**
 * <p>
 * 判断一个数字是否是奇数
 *
 * @Author niujinpeng
 * @Date 2019/3/25 12:00
 */
public class GetIsOdd {

    public static void main(String[] args) {
        boolean odd = isOdd(99999);
        System.out.println(odd);
    }

    public static boolean isOdd(int number) {
        return number % 2 == 1;
    }
}
