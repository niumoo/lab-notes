package net.codingme.box.image;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 文件操作工具类
 *
 * @Author niujinpeng
 * @Date 2019/4/27 10:40
 */
@Slf4j
public class FileUtils {

    /**
     * 读取一个文件到 StringBuilder
     * 
     * @param filePath
     * @return
     */
    public static StringBuilder readFile(String filePath) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            return new StringBuilder(StringUtils.EMPTY);
        }
        FileReader fileReader = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferreader = null;
        try {
            fileReader = new FileReader(filePath);
            bufferreader = new BufferedReader(fileReader);
            char[] chars = new char[1024];
            int len = 0;
            while ((len = bufferreader.read(chars)) != -1) {
                sb.append(chars, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.info("文件不存在,filePath={}", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("文件读取异常,filePath={}", filePath);
        } finally {
            if (bufferreader != null) {
                bufferreader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
        return sb;
    }

    /**
     * 把字符串写入到文件
     *
     * @param sb
     * @param filePath
     * @return
     */
    public static boolean saveFile(StringBuilder sb, String filePath) {
        if (StringUtils.isEmpty(sb) || StringUtils.isEmpty(filePath)) {
            return false;
        }
        // 定义目的地
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(sb.toString());
            bw.flush();
            log.info("文件已经保存至: {}", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 遍历目录下的所有文件路径
     * 
     * @param filePath
     * @return
     */
    public static List<String> listFilePath(String filePath) {
        List<String> files = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()){
            log.info("{} 路径不存在",filePath);
            return files;
        }
        File[] listFiles = file.listFiles();
        for (File f : listFiles) {
            if (f.isFile()) {
                files.add(f.getAbsolutePath());
                log.info("已经扫到到文件:{}", f.getAbsolutePath());
            } else if (f.isDirectory()) {
                String path = f.getAbsolutePath();
                List<String> fileList = listFilePath(path);
                files.addAll(fileList);
            }
        }
        return files;
    }

    /**
     * 下载指定链接的文件到指定路径
     * 
     * @param httpUrl
     * @param savePath
     */
    public static void downloadPicture(String httpUrl, String savePath) {
        if (StringUtils.isEmpty(httpUrl)) {
            log.info("链接不存在");
            return;
        }
        log.info("开始下载文件 {},准备保存到 {}", httpUrl, savePath);
        URL url = null;
        try {
            url = new URL(httpUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context = output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            log.info("{} 已经下载到 {}", httpUrl, savePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
