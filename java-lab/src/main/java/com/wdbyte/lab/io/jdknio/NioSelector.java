package com.wdbyte.lab.io.jdknio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * NIO-Selector
 * 选择器的使用测试
 * Selector（选择器）是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读
 * 写事件做好准备的组件。这样，一个单独的线程可以管理多个channel，从而管理多个网络连接
 * 。我们也可以称Selector为轮询代理器，事件订阅器或者channel容器管理器。
 * 应用程序将向Selector对象注册需要它关注的Channel，以及具体的某一个Channel会对哪些
 * IO事件感兴趣。Selector中也会维护一个“已经注册的Channel”的容器。
 *
 * @Author niujinpeng
 * @Date 2018/10/26 15:31
 */
public class NioSelector {

    public static void main(String[] args) throws IOException {
        // 获取channel
        ServerSocketChannel channel = ServerSocketChannel.open();
        // channel是否阻塞
        channel.configureBlocking(false);
        // 监听88端口
        ServerSocket socket = channel.socket();
        socket.bind(new InetSocketAddress(83));


        // 创建选择器Selector
        Selector selector = Selector.open();
        // 像选择器中注册channel
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 阻塞到有一个就绪
            int readyChannel = selector.select();
            if (readyChannel == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                // 是否可以接受
                if (selectionKey.isAcceptable()) {
                    System.out.println("准备就绪");
                    SelectableChannel selectableChannel = selectionKey.channel();
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectableChannel;
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 注册感兴趣事件-读取
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(2048));
                } else if (selectionKey.isConnectable()) {
                    System.out.println("已连接");

                } else if (selectionKey.isReadable()) {
                    System.out.println("可以读取");

                } else if (selectionKey.isWritable()) {
                    System.out.println("可以写入");

                }
            }
        }
    }
}
