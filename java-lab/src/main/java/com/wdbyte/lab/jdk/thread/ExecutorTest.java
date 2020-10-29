package com.wdbyte.lab.jdk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 *
 * @Author niujinpeng
 * @Date 2018/12/4 10:33
 */
public class ExecutorTest {

    public static ExecutorService executorService = Executors.newFixedThreadPool(50);


    public static void main(String[] args) {
        executorService.submit(new ThreadTest());
        int threadCount = ((ThreadPoolExecutor) executorService).getActiveCount();
        int poolSize = ((ThreadPoolExecutor) executorService).getPoolSize();
        System.out.println("当前线程数量：" + threadCount + ",当前池中线程数：" + poolSize);
    }
}

class ThreadTest implements  Runnable{

    @Override
    public void run() {
        System.out.println("启动了");
        while (true){

        }
    }
}
