package com.wdbyte.lab.io.jdknio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO-Channel
 * <p>
 * 通道Channel和流类似，不同的是通道的工作模式可以是全双工。
 * 也就是说既可以读取，也可以写入。同时也可以异步的进行读写。
 * `Channel`连接着底层数据与缓冲区`Buffer`。
 *
 * @Author niujinpeng
 * @Date 2018/10/25 16:27
 */
public class NioChannel {

    public static void main(String[] args) throws IOException {
        // 申请一个大小为1024bytes的缓冲buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 初始化Channel数据
        FileInputStream fis = new FileInputStream("f:/test.txt");
        FileChannel channel = fis.getChannel();
        System.out.println("Init Channel size：" + channel.size());

        // 从channel中读取数据
        int read = channel.read(byteBuffer);
        System.out.println("Read Size :" + read);
        System.out.println("byteBuffer:"+byteBuffer);

        // 切换到读取模式
        byteBuffer.flip();

        // 输出byteBuffer内容
        System.out.print("print byteBuffer：");
        while (byteBuffer.hasRemaining()){
            System.out.print((char) byteBuffer.get());
        }

        byteBuffer.clear();
        System.out.println(byteBuffer);
        fis.close();
    }
}
