package com.wdbyte.lab.io.bio;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO服务端
 * <p>
 * 单线程阻塞的服务器端
 *
 * @Author niujinpeng
 * @Date 2018/10/15 11:17
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(83);
        try {
            while (true) {
                // 阻塞，直到有数据准备完毕
                Socket socket = serverSocket.accept();

                // 开始收取信息
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                Integer sourcePort = socket.getPort();
                int maxLen = 1024 * 2;
                byte[] contextBytes = new byte[maxLen];

                // 阻塞，直到有数据准备完毕
                int realLen = input.read(contextBytes, 0, maxLen);
                // 读取信息
                String message = new String(contextBytes, 0, realLen);

                // 输出接收信息
                System.out.println("服务器收到来自端口【" + sourcePort + "】的信息：" + message);
                // 响应信息
                output.write("Done!".getBytes());

                // 关闭
                output.close();
                input.close();
                socket.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
