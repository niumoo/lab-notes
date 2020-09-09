package net.codingme.algorithm;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * <p>
 * 根据指定字符串切分字符串
 *
 * @Author niujinpeng
 * @Date 2019/1/28 17:11
 */
public class SplitString {

    public static void main(String[] args) {
        String[] strings = split("123a123a3242a42432a1231a", "a");
        for (String string : strings) {
            System.out.println(string);
        }

    }

    public static String[] split(String source, String str) {

        if (StringUtils.isEmpty(source)) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        if (StringUtils.isEmpty(str)) {
            arrayList.add(source);
            String[] strings = (String[]) arrayList.toArray();
            return strings;
        }

        char[] sourceChars = source.toCharArray();
        char[] chars = str.toCharArray();
        String temp = new String();
        // 分割字符只有一位
        if (chars.length == 1) {
            for (char aChar : sourceChars) {
                if (aChar == chars[0] && StringUtils.isNotEmpty(temp)) {
                    arrayList.add(temp);
                    temp = new String();
                } else {
                    temp = temp + aChar;
                }
            }
        } else {
            for (int i = 0; i < sourceChars.length; i++) {
                char aChar = sourceChars[i];
                if (aChar == chars[0] && StringUtils.isNotEmpty(temp)) {

                    arrayList.add(temp);
                    temp = new String();
                } else {
                    temp = temp + aChar;
                }

            }
        }
        String[] strings = new String[arrayList.size()];
        arrayList.toArray(strings);
        return strings;
    }
}
