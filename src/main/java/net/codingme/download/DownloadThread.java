package net.codingme.download;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

/**
 * <p>
 * 多线程下载工具类
 *
 * @author niujinpeng
 * @Date 2020/7/19 21:26
 */
public class DownloadThread implements Callable<Boolean> {

    // 每次读取的数据块大小
    private static int BYTE_SIZE = 1024 * 100;
    // 下载链接
    private String url;
    // 下载开始位置
    private long startPos;
    // 要下载的文件区块大小
    private Long needDownSize;
    // 标识多线程下载切分的第几部分
    private Integer part;

    public DownloadThread(String url, long startPos, long needDownSize, Integer part) {
        this.url = url;
        this.startPos = startPos;
        this.needDownSize = needDownSize;
        this.part = part;
    }

    @Override
    public Boolean call() throws Exception {
        String httpFileName = HttpUtls.getHttpFileName(url);
        if (part != null) {
            httpFileName = httpFileName + ".temp" + part;
        }
        Long localFileContentLength = FileUtils.getFileContentLength(httpFileName);
        if (localFileContentLength >= needDownSize) {
            System.out.println("> " + httpFileName + "已经下载完毕");
            return true;
        }
        HttpURLConnection httpUrlConnection = HttpUtls.getHttpUrlConnection(url, startPos + localFileContentLength);
        // 获得输入流
        try (InputStream input = httpUrlConnection.getInputStream(); BufferedInputStream bis = new BufferedInputStream(input);
            RandomAccessFile oSavedFile = new RandomAccessFile(httpFileName, "rw")) {
            // 文件写入开始位置 localFileContentLength
            oSavedFile.seek(localFileContentLength);

            Long downloadSize = localFileContentLength;
            byte[] buffer = new byte[BYTE_SIZE]; // 数组大小可根据文件大小设置a
            int len = -1;
            double start = System.currentTimeMillis(), end = 0;
            int logSize = 0;
            while ((len = bis.read(buffer)) != -1) { // 读到文件末尾则返回-1
                oSavedFile.write(buffer, 0, len);
                downloadSize += len;
                // 要下载完毕，重新计算要下载的数据量
                if (downloadSize + BYTE_SIZE >= needDownSize) {
                    Long size = needDownSize - downloadSize;
                    buffer = new byte[size.intValue()];
                }
                // 下载大小(100kb) / 下载时间 = 速度
                end = System.currentTimeMillis();
                Double speed = len / ((end - start) / 1000d) / 1024;
                Double fileSize = FileUtils.getFileContentLength(httpFileName) / 1024d / 1024d;
                String speedLog = "> 已经下载:" + String.format("%.2f", fileSize) + "mb,下载速度：" + speed.intValue() + "kb/s";
                for (int i = 0; i < logSize; i++) {
                    System.out.print("\b");
                }
                System.out.print(speedLog);
                logSize = speedLog.length();
                start = System.currentTimeMillis();
                if (downloadSize >= needDownSize) {
                    System.out.println("\r\n> " + httpFileName + "已经下载完毕");
                    return true;
                }
            }
            System.out.println("\r\n> " + httpFileName + "已经下载完毕");
        } catch (FileNotFoundException e) {
            System.out.println("\n> ERROR! 要下载的文件路径不存在 " + url);
            return false;
        } catch (Exception e) {
            System.out.println("\n> 下载出现异常");
            e.printStackTrace();
            return false;
        } finally {
            httpUrlConnection.disconnect();
        }
        return true;
    }
}
