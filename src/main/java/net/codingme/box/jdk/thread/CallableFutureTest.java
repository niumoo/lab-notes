package net.codingme.box.jdk.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFutureTest {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableFutureTest test = new CallableFutureTest();

        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        // 创建两个有返回值的任务
        Callable c1 = test.new MyCallable("A");
        Callable c2 = test.new MyCallable("B");

        // 执行任务并获取Future对象
        Future f1 = pool.submit(c1);
        Future f2 = pool.submit(c2);

        // 从Future对象上获取任务的返回值，并输出到控制台
        System.out.println(">>>" + f1.get().toString());
        System.out.println(">>>" + f2.get().toString());

        // 关闭线程池
        pool.shutdown();
    }

    @SuppressWarnings("unchecked")
    class MyCallable implements Callable {
        private String name;

        MyCallable(String name) {
            this.name = name;
        }

        @Override
        public Object call() throws Exception {
            return name + "任务返回的内容";
        }
    }
}