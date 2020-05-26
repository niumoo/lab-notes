package net.codingme.leetcode;

import java.util.*;

/**
 * 没有重复字符的最长子串
 * <p>
 * 给定一个字符串，找到最长子字符串的长度而不重复字符。
 * <p>
 * Example 1:
 * <p>
 * Input: "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 * Example 2:
 * <p>
 * Input: "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * Example 3:
 * <p>
 * Input: "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * @Author niujinpeng
 * @Date 2018/11/2 17:51
 */
public class L003_LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        // int length = lengthOfLongestSubstringByChar("abcabcbb");
        // System.out.println(length);
    }

    /**
     * 找最大子串，如果已保存的结果中有相同的，清除，然后报错最大长度。
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        String[] sarr = s.split("");
        int max = 0;
        LinkedList<String> list = new LinkedList<>();
        for (String s1 : sarr) {
            if (list.contains(s1)) {
                while (!list.isEmpty()) {
                    if (list.get(0).equals(s1)) {
                        list.remove(0);
                        break;
                    }
                    list.remove(0);
                }
            }
            list.add(s1);
            max = list.size() > max ? list.size() : max;
        }
        return max;
    }

    /**
     * 使用字符串解决
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstringByString(String s) {
        int max = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            // 当前字符
            String ch = s.substring(i, i + 1);
            // 缓存字符串
            String tempStr = s.substring(start, i);
            // 缓存包含当前字符
            if (tempStr.contains(ch)) {
                // 找到字符去除
                int indexOf = tempStr.indexOf(ch);
                start = indexOf + 1;
            }
            max = (i + 1) - start > max ? (i + 1) - start : max;
        }
        return max;
    }

    public static int lengthOfLongestSubstringByHashSet(String s) {
        int max = 0;
        List<Object> list = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!list.contains(c)) {
                list.add(c);
                max = Math.max(list.size(), max);
            } else {
                int indexOf = list.indexOf(c);
                list = list.subList(indexOf + 1, list.size());
                list.add(c);
            }
        }
        return max;
    }
}
