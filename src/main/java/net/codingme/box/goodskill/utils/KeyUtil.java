package net.codingme.box.goodskill.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 * 
 *
 * @Author niujinpeng
 * @Date 2019/1/31 17:28
 */
public class KeyUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    /**
     * 生成唯一主键 格式：时间+随机数
     * 
     * @return
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer randNumber = random.nextInt(900000) + 100000;
        return sdf.format(new Date()) + String.valueOf(randNumber);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            System.out.println(UUID.randomUUID());
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

}
