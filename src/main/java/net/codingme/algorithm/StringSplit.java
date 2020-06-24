package net.codingme.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字符串分批
 * <p>
 * 给定一个存储了不重复字符串的集合，以及一个分批数，对字符串集合进行尽可能均匀的分批
 * 例如：字符串集合为["a", "b", "c"]，分批数为 2，则返回：[["a", "b"], ["c"]]
 * 注：返回 [["a"], ["b", "c"]] 也可以
 *
 * @author niujinpeng
 * @Date 2020/6/23 8:19
 */
public class StringSplit {

    public static void main(String[] args) {
        String content = "1234567890";
        int size = 6;
        List<String> stringList = split(content, size);
        for (String s : stringList) {
            System.out.println(s);
        }
    }

    public static List<String> split(String content, int size) {
        List<String> list = new ArrayList<>();
        int yu = content.length() % size;
        int sizeTemp = content.length() / size;
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (i < yu) {
                String result = content.substring(index, index + sizeTemp + 1);
                index = index + sizeTemp + 1;
                list.add(result);
            } else {
                String result = content.substring(index, index + sizeTemp);
                index = index + sizeTemp;
                list.add(result);
            }
        }
        return list;
    }
}
