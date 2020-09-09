package net.codingme.offer;

/**
 * <p>
 * 替换字符串中的空格，为 %20,
 * 注意效率
 *
 * @author niujinpeng
 * @Date 2020/6/24 16:00
 */
public class StringReplaceBlank {

    public static void main(String[] args) {
        String content = "my java code";

        // JDK 实现
        System.out.println(content.replace(" ", "%20"));
        System.out.println(replace(content));
    }

    public static String replace(String content) {
        if (content == null) {
            return content;
        }
        char[] chars = content.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char aChar : chars) {
            if (aChar == ' ') {
                sb.append("%20");
            } else {
                sb.append(aChar);
            }
        }
        return sb.toString();
    }


}
