package net.codingme.box.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 * BIO客户端
 * 模拟20个客户端并发请求，服务端则使用单线程。
 *
 * @Author niujinpeng
 * @Date 2018/10/15 10:50
 */
public class SocketClient {
    public static void main(String[] args) throws InterruptedException {
        // 客户端数量
        Integer clientNumber = 20;
        CountDownLatch countDownLatch = new CountDownLatch(clientNumber);

        // 分别启动20个客户端
        for (int index = 0; index < clientNumber; index++, countDownLatch.countDown()) {
            SocketClientRequestThread client = new SocketClientRequestThread(countDownLatch, index);
            new Thread(client).start();
        }

        synchronized (SocketClient.class) {
            SocketClient.class.wait();
        }
    }
}

