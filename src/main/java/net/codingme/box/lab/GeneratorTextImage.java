package net.codingme.box.lab;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import jdk.nashorn.internal.ir.IfNode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;

/**
 * <p>
 * 根据图片生成字符图案
 * 1.图片大小缩放
 * 2.图片灰度化处理
 * 3.获取图片像素点颜色
 * 4.匹配字符
 * 5.输出图案
 *
 * @Author niujinpeng
 * @Date 2018/12/4 16:29
 */
public class GeneratorTextImage {

    private static final char[] PIXEL = {' ', '.', '*', ':', 'o', '&', '8', '#', '@'};

    public static void main(String[] args) throws Exception {
        // 图片缩放
        BufferedImage bufferedImage = makeSmallImage("dog.jpg");
        // 灰化处理
        bufferedImage = grayImage(bufferedImage);
        // 匹配图案
        print(bufferedImage);
    }

    /**
     * 输出图案
     *
     * @param bufferedImage
     */
    public static void print(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = bufferedImage.getRGB(j, i);
                int b = (rgb & 255);
                int index = b / (255 / PIXEL.length);
                if (index == 0) {
                    index = 1;
                }
                System.out.print(PIXEL[Math.abs(index - 9)]);
            }
            System.out.println();
        }
    }

    /**
     * 灰化处理
     *
     * @return
     */
    public static BufferedImage grayImage(BufferedImage image) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();
        //重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }
        return grayImage;
    }


    /**
     * 生成缩略图
     *
     * @param srcImageName 源文件
     * @throws Exception
     */
    public static BufferedImage makeSmallImage(String srcImageName) throws Exception {
        File srcImageFile = new File(srcImageName);
        if (srcImageFile == null) {
            System.out.println("文件不存在");
            return null;
        }
        FileOutputStream fileOutputStream = null;
        BufferedImage tagImage = null;
        Image srcImage = null;
        try {
            srcImage = ImageIO.read(srcImageFile);
            int srcWidth = srcImage.getWidth(null);//原图片宽度
            int srcHeight = srcImage.getHeight(null);//原图片高度
            int dstMaxSize = 300;//目标缩略图的最大宽度/高度，宽度与高度将按比例缩写
            int dstWidth = srcWidth;//缩略图宽度
            int dstHeight = srcHeight;//缩略图高度
            float scale = 0;
            //计算缩略图的宽和高
            if (srcWidth > dstMaxSize) {
                dstWidth = dstMaxSize;
                scale = (float) srcWidth / (float) dstMaxSize;
                dstHeight = Math.round((float) srcHeight / scale);
            }
            srcHeight = dstHeight;
            if (srcHeight > dstMaxSize) {
                dstHeight = dstMaxSize;
                scale = (float) srcHeight / (float) dstMaxSize;
                dstWidth = Math.round((float) dstWidth / scale);
            }
            //生成缩略图
            tagImage = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_RGB);
            tagImage.getGraphics().drawImage(srcImage, 0, 0, dstWidth, dstHeight, null);
            return tagImage;
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                }
                fileOutputStream = null;
            }
            tagImage = null;
            srcImage = null;
            System.gc();
        }
    }
}
