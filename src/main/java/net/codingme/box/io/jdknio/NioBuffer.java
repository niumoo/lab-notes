package net.codingme.box.io.jdknio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * NIO-Buffer
 * <p>
 * Buffer本质是可以写入可以读取的内存，这块内存被包装成了NIO的Buffer对象，
 * 然后为它提供一组用于访问的方法。Java则为java.nio.Buffer实现了基本数据
 * 类型的Buffer
 *
 * @Author niujinpeng
 * @Date 2018/10/24 18:28
 */
public class NioBuffer {

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 申请一个大小为1024bytes的缓冲buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("申请到的Buffer：" + byteBuffer);

        // 写入helloworld到buffer
        byteBuffer.put("HelloWorld".getBytes());
        System.out.println("写入HelloWorld到Buffer：" + byteBuffer);

        // 切换为读模式
        byteBuffer.flip();
        // 当前Buffer已存放的大小
        int length = byteBuffer.remaining();
        byte[] bytes = new byte[length];

        // 读取bytes长度的数据
        byteBuffer.get(bytes);
        System.out.println("从buffer读取到数据：" + new String(bytes, "UTF-8"));

        // 切换为compact 清空已读取的数据
        byteBuffer.compact();
        System.out.println("读取后的Buffer：" + byteBuffer);
    }
}
