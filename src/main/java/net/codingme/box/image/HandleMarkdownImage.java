package net.codingme.box.image;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 把 MD 文件中的微博链接提取出来 <br/>
 * 1. 遍历所有 markdown 文件 <br/>
 * 2. 取出文件中的图片 url 同时替换为新图片 url 路径 <br/>
 * 3. 把图片下载到本地<br/>
 * 4. 上传图片到 github 仓库
 * </p>
 *
 * @Author niujinpeng
 * @Date 2019/4/27 10:37
 */
@Slf4j
public class HandleMarkdownImage {
    /**
     * 源 Markdown 文件文件夹
     */
    public static String SRC_FILE_PATH = "_posts";
    /**
     * 图片本地备份路径
     */
    public static String IMAGE_SAVE_PATH = "f:/static-image3/";
    /**
     * 存放到 Github 仓库的路径前缀
     */
    public static String IMAGE_PREFIX_NOW = "https://raw.githubusercontent.com/niumoo/files/master/images/blog/";

    public static void main(String[] args) throws IOException {
        // 遍历文件
        List<String> files = FileUtils.listFilePath(SRC_FILE_PATH);
        // 图片信息
        HashMap<String, String> urlMap = new HashMap<>();
        for (String filePath : files) {
            StringBuilder sb = FileUtils.readFile(filePath);
            String content = new StringBuilder(sb.toString()).toString();
            int indexStart = -1;
            while ((indexStart = sb.indexOf("](http")) != -1) {
                indexStart = indexStart + 8;
                int indexEnd = sb.indexOf(")", indexStart);
                String url = sb.substring(indexStart - 6, indexEnd);
                if (StringUtils.contains(url, "githubusercontent")) {
                    sb = new StringBuilder(sb.substring(indexEnd));
                    continue;
                }
                if (url.endsWith("jpg") || url.endsWith("png") || url.endsWith("gif")) {
                    int lastIndexOf = url.lastIndexOf(".");
                    String fileName = MD5.encode(url) + url.substring(lastIndexOf, lastIndexOf + 4);
                    urlMap.put(fileName, url);
                    content = content.replace(url, IMAGE_PREFIX_NOW + fileName);
                }
                sb = new StringBuilder(sb.substring(indexEnd));
            }
             FileUtils.saveFile(new StringBuilder(content), filePath);
        }
        log.info("共发现图片{}张", urlMap.size());
        urlMap.forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
        // 备份文件夹是否存在
        File imageSavePath = new File(IMAGE_SAVE_PATH);
        if (!imageSavePath.exists()) {
            imageSavePath.mkdir();
        }
        // 备份图片到本地
         urlMap.forEach((key, value) -> {
         String savePath = IMAGE_SAVE_PATH + key;
         FileUtils.downloadPicture(value, savePath);
         });
    }

}
