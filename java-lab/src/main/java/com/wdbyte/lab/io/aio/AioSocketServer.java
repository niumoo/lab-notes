package com.wdbyte.lab.io.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AIO异步通信
 * <p>
 * Java 异步IO
 * 在java的N-IO框架中，需要使用一个重要的selector负责代替应用查询已注册的通道，用于IO事件轮训，
 * 管理通道集合等。
 *
 * A-IO中不是“轮训”方式，而是订阅-通知的方式，所以不需要`selector`了，channel通道可以直接注册
 * 监听到操作系统。
 *
 * @Author niujinpeng
 * @Date 2018/10/28 15:58
 */
public class AioSocketServer {


    private static Object waitObject = new Object();

    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open();

        // 监听IP的83端口
        serverSocket.bind(new InetSocketAddress("0.0.0.0", 83));
        // 注册监听
        serverSocket.accept(null, new ServerSocketChannelHandle(serverSocket));

        synchronized (waitObject) {
            waitObject.wait();
        }
    }
}

/**
 * 用于响应ServerSocketChannel事件
 */
class ServerSocketChannelHandle implements CompletionHandler<AsynchronousSocketChannel, Void> {

    private static final Logger logger = LoggerFactory.getLogger(ServerSocketChannelHandle.class);

    private AsynchronousServerSocketChannel serverSocketChannel;

    ServerSocketChannelHandle(AsynchronousServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }


    @Override
    public void completed(AsynchronousSocketChannel socketChannel, Void attachment) {
        logger.info("completed(AsynchronousSocketChannel,Void)");
        // 注册监听
        this.serverSocketChannel.accept(attachment, this);

        // 注册Read事件
        ByteBuffer readBuffer = ByteBuffer.allocate(50);
        socketChannel.read(readBuffer, new StringBuffer(), new SocketChannelReadHandle(socketChannel, readBuffer));
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        logger.info("failed(Throwable exc, Void attachment)");
    }
}

/**
 * 负责对每一个SocketChannel的数据获取事件进行监听
 */
class SocketChannelReadHandle implements CompletionHandler<Integer, StringBuffer> {

    private static final Logger logger = LoggerFactory.getLogger(SocketChannelReadHandle.class);

    private AsynchronousSocketChannel socketChannel;

    private ByteBuffer byteBuffer;

    SocketChannelReadHandle(AsynchronousSocketChannel socketChannel, ByteBuffer byteBuffer) {
        this.socketChannel = socketChannel;
        this.byteBuffer = byteBuffer;

    }

    @Override
    public void completed(Integer result, StringBuffer historyContext) {
        // 如果条件成立，说明客户端主动终止了TCP套接字
        if (result == -1) {
            try {
                this.socketChannel.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
            return;
        }

        logger.info("completed(Integer result, StringBuffer historyContext)");
        this.byteBuffer.flip();
        // 如果接收用的byte长度不够，AIO会自动处理成多次通知
        byte[] contexts = new byte[1024];
        this.byteBuffer.get(contexts, 0, result);
        this.byteBuffer.clear();
        try {
            String nowContent = new String(contexts, 0, result, "UTF-8");
            historyContext.append(nowContent);
            logger.info("现在接受到的数据：" + historyContext);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.toString());
        }

        // 是否有结束标记
        if (historyContext.indexOf("over") == -1) {
            return;
        }

        // 收到"over“开始处理数据
        logger.info("开始处理数据...");
        // 清空
        historyContext = new StringBuffer();

        // 继续监听
        this.socketChannel.read(this.byteBuffer, historyContext, this);


    }

    @Override
    public void failed(Throwable exc, StringBuffer attachment) {
        logger.info("客户端异常关闭，服务端关闭TCP通道");
        try {
            this.socketChannel.close();
        } catch (IOException e) {
            logger.info(e.toString());
        }
    }
}