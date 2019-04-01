package net.codingme.box.lab;

/**
 * <p>
 *
 * @Author niujinpeng
 * @Date 2019/3/29 11:28
 */
public class Returni {

    public static void main(String[] args) {
        System.out.println(calc(5));
    }

    public static int calc(int i) {
        try {
            ++i;
            return i++;
        } finally {
            i++;
        }
    }
}
