package com.wdbyte.decompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.benf.cfr.reader.util.functors.UnaryFunction;

/**
 * @author https://github.com/niumoo
 * @date 2021/05/16
 */
public class HardCode <A, B> {
    public HardCode(A a, B b) { }

    public static void test(int... args) { }

    public static void main(String... args) {
        test(1, 2, 3, 4, 5, 6);
    }

    int byteAnd0() {
        int b = 1;
        int x = 0;
        do {
            b = (byte)((b ^ x));
        } while (b++ < 10);
        return b;
    }

    private void a(Integer i) {
        a(i);
        b(i);
        c(i);
    }

    private void b(int i) {
        a(i);
        b(i);
        c(i);
    }

    private void c(double d) {
        c(d);
        d(d);
    }

    private void d(Double d) {
        c(d);
        d(d);
    }

    private void e(Short s) {
        b(s);
        c(s);
        e(s);
        f(s);
    }

    private void f(short s) {
        b(s);
        c(s);
        e(s);
        f(s);
    }

    void test1(String path) {
        try {
            int x = 3;
        } catch (NullPointerException t) {
            System.out.println("File Not found");
            if (path == null) { return; }
            throw t;
        } finally {
            System.out.println("Fred");
            if (path == null) { throw new IllegalStateException(); }
        }
    }

    private final List<Integer> stuff = new ArrayList<>();{
        stuff.add(1);
        stuff.add(2);
    }

    public static int plus(boolean t, int a, int b) {
        int c = t ? a : b;
        return c;
    }

    // Lambda
    Integer lambdaInvoker(int arg, UnaryFunction<Integer, Integer> fn) {
        return fn.invoke(arg);
    }

    // Lambda
    public int testLambda() {
        return lambdaInvoker(3, x -> x + 1);
        //        return 1;
    }

    // Lambda
    public Integer testLambda(List<Integer> stuff, int y, boolean b) {
        return stuff.stream().filter(b ? x -> x > y : x -> x < 3).findFirst().orElse(null);
    }

    // stream
    public static <Y extends Integer> void testStream(List<Y> list) {
        IntStream s = list.stream()
            .filter(x -> {
                System.out.println(x);
                return x.intValue() / 2 == 0;
                })
            .map(x -> (Integer)x+2)
            .mapToInt(x -> x);
        s.toArray();
    }

    // switch
    public void testSwitch1(){
        int i = 0;
        switch(((Long)(i + 1L)) + "") {
            case "1":
                System.out.println("one");
        }
    }
    // switch
    public void testSwitch2(String string){
        switch (string) {
            case "apples":
                System.out.println("apples");
                break;
            case "pears":
                System.out.println("pears");
                break;
        }
    }

    // switch
    public static void testSwitch3(int x) {
        while (true) {
            if (x < 5) {
                switch ("test") {
                    case "okay":
                        continue;
                    default:
                        continue;
                }
            }
            System.out.println("wow x2!");
        }
    }
}
