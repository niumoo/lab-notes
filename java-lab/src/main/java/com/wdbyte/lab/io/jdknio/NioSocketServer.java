package com.wdbyte.lab.io.jdknio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 使用Java NIO框架，实现一个支持多路复用IO的服务器端
 *
 * @Author niujinpeng
 * @Date 2018/10/16 0:53
 */
public class NioSocketServer {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NioSocketServer.class);

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 是否阻塞
        serverChannel.configureBlocking(false);
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(83));

        Selector selector = Selector.open();
        // 服务器通道只能注册SelectionKey.OP_ACCEPT事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // java程序对多路复用IO的支持也包括了阻塞模式 和非阻塞模式两种。
            if (selector.select(100) == 0) {
                //LOGGER.info("本次询问selector没有获取到任何准备好的事件");
                continue;
            }

            // 询问系统，所有获取到的事件类型
            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()) {
                SelectionKey readKey = selectionKeys.next();
                // 上面获取到的readKey要移除，不然会一直存在selector.selectedKeys()的集合之中
                selectionKeys.remove();

                SelectableChannel selectableChannel = readKey.channel();
                if (readKey.isValid() && readKey.isAcceptable()) {
                    LOGGER.info("--------------channel通道已经准备完毕-------------");
                    /*
                     * 当server socket channel通道已经准备好，就可以从server socket channel中获取socketchannel了
                     * 拿到socket channel后，要做的事情就是马上到selector注册这个socket channel感兴趣的事情。
                     * 否则无法监听到这个socket channel到达的数据
                     * */
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectableChannel;
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    registerSocketChannel(socketChannel, selector);
                } else if (readKey.isValid() && readKey.isConnectable()) {
                    LOGGER.info("--------------socket channel 建立连接-------------");
                } else if (readKey.isValid() && readKey.isReadable()) {
                    LOGGER.info("--------------socket channel 数据准备完成，可以开始读取-------------");
                    try {
                        readSocketChannel(readKey);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
        }

    }

    /**
     * 在server socket channel接收到/准备好 一个新的 TCP连接后。
     * 就会向程序返回一个新的socketChannel。<br>
     * 但是这个新的socket channel并没有在selector“选择器/代理器”中注册，
     * 所以程序还没法通过selector通知这个socket channel的事件。
     * 于是我们拿到新的socket channel后，要做的第一个事情就是到selector“选择器/代理器”中注册这个
     * socket channel感兴趣的事件
     *
     * @param socketChannel
     * @param selector
     * @throws Exception
     */
    private static void registerSocketChannel(SocketChannel socketChannel, Selector selector) {
        // 是否阻塞
        try {
            socketChannel.configureBlocking(false);
            // 读模式只能读，写模式可以同时读
            // socket通道可以且只可以注册三种事件SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT
            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(2048));
        } catch (IOException e) {
            LOGGER.info(e.toString(), e);
        }

    }

    private static void readSocketChannel(SelectionKey readKey) throws Exception {
        SocketChannel clientSocketChannel = (SocketChannel) readKey.channel();
        //获取客户端使用的端口
        InetSocketAddress sourceSocketAddress = (InetSocketAddress) clientSocketChannel.getRemoteAddress();
        int sourcePort = sourceSocketAddress.getPort();

        // 拿到这个socket channel使用的缓存区，准备读取数据
        // 解缓存区的用法概念，实际上重要的就是三个元素capacity,position和limit。
        ByteBuffer contextBytes = (ByteBuffer) readKey.attachment();
        // 通道的数据写入到【缓存区】
        // 由于之前设置了ByteBuffer的大小为2048 byte，所以可以存在写入不完的情况，需要调整
        int realLen = -1;
        try {
            realLen = clientSocketChannel.read(contextBytes);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            clientSocketChannel.close();
            return;
        }

        // 如果缓存中没有数据
        if (realLen == -1) {
            LOGGER.warn("--------------缓存中没有数据-------------");
            return;
        }

        // 将缓存区读写状态模式进行切换
        contextBytes.flip();
        // 处理编码问题
        byte[] messageBytes = contextBytes.array();
        String messageEncode = new String(messageBytes, "UTF-8");
        String message = URLDecoder.decode(messageEncode, "UTF-8");

        // 接受到了"over"则清空buffer,并响应,否则不清空缓存，并还原Buffer写状态
        if (message.indexOf("over") != -1) {
            //清空已经读取的缓存，并从新切换为写状态(这里要注意clear()和capacity()两个方法的区别)
            contextBytes.clear();
            LOGGER.info("端口【" + sourcePort + "】客户端发来的信息：" + message);
            LOGGER.info("端口【" + sourcePort + "】客户端消息发送完毕");
            // 响应
            ByteBuffer sendBuffer = ByteBuffer.wrap(URLEncoder.encode("Done!", "UTF-8").getBytes());
            clientSocketChannel.write(sendBuffer);
            clientSocketChannel.close();
        } else {
            LOGGER.info("端口【" + sourcePort + "】客户端发来的信息还未完毕，继续接收");
            // limit和capacity的值一致，position的位置是realLen的位置
            contextBytes.position(realLen);
            contextBytes.limit(contextBytes.capacity());
        }
    }


}
