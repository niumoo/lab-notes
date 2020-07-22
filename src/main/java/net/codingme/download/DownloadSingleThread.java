package net.codingme.download;

import java.io.*;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;

/**
 * <p>
 * 单线程断点续传下载器
 *
 * @author niujinpeng
 * @Date 2020/7/21 8:01
 */
public class DownloadSingleThread {

    // 每次读取的数据块大小
    private static int BYTE_SIZE = 1024 * 100;

    public static void main(String[] args) throws Exception {
        // String url = "http://wppkg.baidupcs.com/issue/netdisk/yunguanjia/BaiduYunGuanjia_7.0.1.1.exe";
        String url = "https://download-cf.jetbrains.com/datagrip/datagrip-2020.1.5.exe";
        new DownloadSingleThread().download(url);
    }

    public boolean download(String url) throws IOException {
        if (url == null || url.trim() == "") {
            throw new RuntimeException("> 下载路径不正确");
        }
        // 文件名
        String httpFileName = HttpUtls.getHttpFileName(url);
        // 本地文件大小
        Long localFileContentLength = FileUtils.getFileContentLength(httpFileName);
        // 网络文件大小
        long httpFileContentLength = HttpUtls.getHttpFileContentLength(url);
        if (localFileContentLength >= httpFileContentLength) {
            System.out.println("> " + httpFileName + " 已经下载完毕，无需重复下载");
            return true;
        }
        if (localFileContentLength > 0) {
            System.out.println("> 开始断点续传 " + httpFileName);
        } else {
            System.out.println("> 开始下载文件 " + httpFileName);
        }
        System.out.println("> 开始下载时间 " + LocalDateTime.now());
        HttpURLConnection httpUrlConnection = HttpUtls.getHttpUrlConnection(url, localFileContentLength);
        // 获得输入流
        try (InputStream input = httpUrlConnection.getInputStream(); BufferedInputStream bis = new BufferedInputStream(input);
            RandomAccessFile oSavedFile = new RandomAccessFile(httpFileName, "rw")) {
            // 文件写入开始位置 localFileContentLength
            oSavedFile.seek(localFileContentLength);

            byte[] buffer = new byte[BYTE_SIZE]; // 数组大小可根据文件大小设置a
            int len = -1;
            double start = System.currentTimeMillis(), end = 0, downloadSize = 0;
            int logSize = 0;
            while ((len = bis.read(buffer)) != -1) { // 读到文件末尾则返回-1
                oSavedFile.write(buffer, 0, len);
                downloadSize += len;
                // 下载大小(100kb) / 下载时间 = 速度
                end = System.currentTimeMillis();
                if (end - start >= 1000) {
                    Double speed = (downloadSize / 1024) / ((end - start) / 1000d);
                    long fileContentLength = FileUtils.getFileContentLength(httpFileName);
                    Double fileSize = fileContentLength / 1024d / 1024d;
                    double surplusTime = (httpFileContentLength - fileContentLength) / 1024d / speed;
                    String speedLog =
                        "> 已经下载大小:" + String.format("%.2f", fileSize) + "mb,当前下载速度:" + speed.intValue() + "kb/s" + ",估计剩余时间:" + String.format("%.1f", surplusTime) + "s";
                    for (int i = 0; i < logSize; i++) {
                        System.out.print("\b");
                    }
                    System.out.print(speedLog);
                    logSize = speedLog.length();
                    start = System.currentTimeMillis();
                    downloadSize = 0;
                }
            }
            System.out.println("\r\n> 已经下载完毕 " + httpFileName);
        } catch (FileNotFoundException e) {
            System.out.println("\n> ERROR! 要下载的文件路径不存在 " + url);
            return false;
        } catch (Exception e) {
            System.out.println("\n> 下载出现异常");
            e.printStackTrace();
            return false;
        } finally {
            httpUrlConnection.disconnect();
            System.out.println("> 结束下载时间 " + LocalDateTime.now());
        }
        return true;
    }

}
