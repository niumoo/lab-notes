package net.codingme.download;

/**
 * <p>
 *
 * 多线程下载
 * 断点续传下载 demo
 * 
 * @author niujinpeng
 * @Date 2020/7/15 15:14
 */
public class DownloadMain {

    // 下载线程数量
    private static int THREAD_NUM = 5;

    public static void main(String[] args) throws Exception {
        String url = "http://wppkg.baidupcs.com/issue/netdisk/yunguanjia/BaiduYunGuanjia_7.0.1.1.exe";
        DownloadMain fileDownload = new DownloadMain();
        fileDownload.download(url);
    }

    public boolean download(String url) throws Exception {
        if (url == null || url.trim() == "") {
            throw new RuntimeException("> 下载路径不正确");
        }
        String fileName = HttpUtls.getHttpFileName(url);
        long localFileSize = FileUtils.getFileContentLength(fileName);
        // 获取网络文件具体大小
        long httpFileContentLength = HttpUtls.getHttpFileContentLength(url);
        if (localFileSize > 0 && localFileSize >= httpFileContentLength) {
            System.out.println("> " + fileName + "已经下载完毕，无需重新下载");
            return true;
        }
        if (localFileSize > 0) {
            System.out.println("> 开始断点续传 " + fileName);
        } else {
            System.out.println("> 开始下载文件 " + fileName);
        }
        DownloadThread downloadThread = new DownloadThread(url, 0, httpFileContentLength, 0);
        Boolean aBoolean = downloadThread.call();
        return true;
    }

}
