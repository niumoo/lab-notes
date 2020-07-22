package net.codingme.download;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网络请求操作工具类
 *
 * @author niujinpeng
 * @Date 2020/7/19 21:25
 */
public class HttpUtls {

    public static HttpURLConnection getHttpUrlConnection(String url) throws IOException {
        URL httpUrl = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection)httpUrl.openConnection();
        httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
        return httpConnection;
    }

    public static HttpURLConnection getHttpUrlConnection(String url, long size) throws IOException {
        HttpURLConnection httpUrlConnection = getHttpUrlConnection(url);
        httpUrlConnection.setRequestProperty("RANGE", "bytes=" + size + "-");
        return httpUrlConnection;
    }

    public static long getHttpFileContentLength(String url) throws IOException {
        HttpURLConnection httpUrlConnection = getHttpUrlConnection(url);
        int contentLength = httpUrlConnection.getContentLength();
        httpUrlConnection.disconnect();
        return contentLength;
    }

    public static String getHttpFileName(String url) {
        int indexOf = url.lastIndexOf("/");
        return url.substring(indexOf + 1);
    }
}
