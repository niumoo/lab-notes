package net.codingme.box.lab;

/**
 * <p>
 *
 * @Author niujinpeng
 * @Date 2019/11/6 23:23
 */
public class JiTu {

    public static void main(String[] args) {
        /**
         * 数学解法
         * 兔子 x 个，鸡 y 个，
         * x + y = n;
         * 4x + 2y = m;
         * 4(n - y) + 2y = m;
         * 4n - 4y + 2y = m;
         * 4n - 2y = m;
         * 4n - m = 2y;
         * (4n - m)/2 = y
         */
        int n = 10;
        int m = 32;
        int ji = (4 * n - m) / 2;
        int tuzi = n - ji;
        if (ji > 0 && ji <= n) {
            System.out.println("兔子：" + tuzi + " 鸡：" + ji);
        } else {
            System.out.println("ERROR");
        }
    }
}
