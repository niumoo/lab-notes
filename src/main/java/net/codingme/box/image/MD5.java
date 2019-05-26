package net.codingme.box.image;

import java.security.MessageDigest;

/**
 * MD5 加密算法
 */
public class MD5 {

    /**
     * md5 加密
     *
     * @param s
     * @return
     */
    public static String encode(String s) {
        try {
            return toHex(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"))).toLowerCase();
        } catch (Exception e) {
            throw new RuntimeException("md5 加密", e);
        }
    }

    /**
     * 十六进制字符
     */
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    /**
     * 转换为十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String toHex(byte[] bytes) {
        StringBuilder str = new StringBuilder(bytes.length * 2);
        // 十六进制中的 15
        final int fifteen = 0x0f;
        // byte 为 32 位
        for (byte b : bytes) {
            // 获取第 25 位到第 28 位的二进制数
            str.append(HEX_CHARS[(b >> 4) & fifteen]);
            // 获取第 29 位到第 32 位的二进制数
            str.append(HEX_CHARS[b & fifteen]);
        }
        return str.toString();
    }
}
