package net.codingme.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 给定一个不存在重复字符的字符串，打印出该字符串的所有排列组合。
 * 例如，输入字符串”abc”，打印：”abc”,”acb”,”bac”,”bca”,”cab”,”cba”
 *
 * @author niujinpeng
 * @Date 2020/6/24 14:57
 */
public class StringCombo {

    public static void main(String[] args) {
        String content = "ABC";
        List<String> list = comboString(content);
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static List<String> comboString(String str) {
        List<String> resultList = new ArrayList<>();
        if (str.length() == 1) {
            resultList.add(str);
            return resultList;
        }
        List<String> list = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list.add(String.valueOf(c));
        }
        List<String> listTemp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listTemp.clear();
            listTemp.addAll(list);
            String remove = listTemp.remove(i);
            StringBuffer sb = new StringBuffer();
            for (String s : listTemp) {
                sb.append(s);
            }
            List<String> stringList = comboString(sb.toString());
            for (String s : stringList) {
                resultList.add(remove + s);
            }
        }
        return resultList;
    }
}
