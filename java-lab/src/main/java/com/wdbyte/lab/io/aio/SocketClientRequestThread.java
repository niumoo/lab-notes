package com.wdbyte.lab.io.aio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 * 网络客户端，用于模拟请求
 *
 * @Author niujinpeng
 * @Date 2018/10/15 10:53
 */
class SocketClientRequestThread implements Runnable {

    private CountDownLatch countDownLatch;

    /**
     * 线程的编号
     */
    private Integer clientIndex;


    public SocketClientRequestThread(CountDownLatch countDownLatch, Integer clientIndex) {
        this.countDownLatch = countDownLatch;
        this.clientIndex = clientIndex;
    }

    @Override
    public void run() {
        Socket socket = null;
        OutputStream clientRequest = null;
        InputStream clientResponse = null;
        try {
            socket = new Socket("localhost", 83);
            clientRequest = socket.getOutputStream();
            clientResponse = socket.getInputStream();

            //等待，直到SocketClientDaemon完成所有线程的启动，然后所有线程一起发送请求
            this.countDownLatch.await();

            // 发送请求信息
            clientRequest.write(("这是第" + this.clientIndex + "个客户端的请求").getBytes());
            clientRequest.flush();

            clientRequest.write("over".getBytes());
            clientRequest.flush();

            // 等待服务器返回消息
            System.out.println("第" + this.clientIndex + "个客户端请求发送完成，等待服务器响应");
            //int maxLen = 1024;
            //byte[] contentBytes = new byte[maxLen];
            //int realLen;
            //String message = "";
            //
            //// 等待服务端返回，in和out不能cloese
            //while ((realLen = clientResponse.read(contentBytes, 0, maxLen)) != -1) {
            //    message += new String(contentBytes, 0, realLen);
            //}
            //System.out.println("第" + this.clientIndex + "个客户端接受到来自服务器的消息:" + message);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientRequest != null) {
                    clientRequest.close();
                }
                if (clientRequest != null) {
                    clientResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}