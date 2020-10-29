package com.wdbyte.lab.io.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 非阻塞IO - 监听非阻塞
 * 通过设置超时时间实现
 *
 * @Author niujinpeng
 * @Date 2018/10/15 14:53
 */
public class SocketServerNioListen {

    private static final Logger logger = LoggerFactory.getLogger(SocketServerNioListen.class);

    private static Object xWait = new Object();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(83);
            serverSocket.setSoTimeout(100);
            while (true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    synchronized (SocketServerNioListen.xWait) {
                        logger.info("没有从底层接收到任务数据报文，等待10ms");
                        SocketServerNioListen.xWait.wait(10);
                    }
                    continue;
                }

                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                Integer sourcePort = socket.getPort();
                int maxLen = 2048;
                byte[] contentBytes = new byte[maxLen];
                int realLen;
                StringBuffer message = new StringBuffer();
                // 读取的时候，程序会阻塞，知道系统把网络传过来的数据准备完毕
                while ((realLen = input.read(contentBytes, 0, maxLen)) != -1) {
                    message.append(new String(contentBytes, 0, realLen));
                    /**
                     * 如果收到over，表示传送完毕
                     */
                    if (message.toString().endsWith("over")) {
                        break;
                    }
                }

                // 输出信息
                logger.info("服务器收到来自端口" + sourcePort + "的消息：" + message.toString());
                // 响应
                output.write("Done!".getBytes());

                output.close();
                input.close();
                socket.close();
            }
        } catch (SocketException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

}

