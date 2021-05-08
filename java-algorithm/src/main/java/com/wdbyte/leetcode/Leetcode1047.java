package com.wdbyte.leetcode;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * 给出由小写字母组成的字符串S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 *
 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
 *
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 *
 * 示例：
 *
 * 输入："abbaca"
 * 输出："ca"
 * 解释：
 * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string
 *
 * @author github.com/niumoo
 * @date 2021/03/09
 */
public class Leetcode1047 {

    public static void main(String[] args) {
        System.out.println(new Leetcode1047().removeDuplicates("acbbaaca"));
        System.out.println(new Leetcode1047().removeDuplicates2("abbaca"));
        System.out.println(new Leetcode224().calculate2(" 2-1 + 2 "));
    }

    /**
     * 执行结果：
     * 通过
     * 显示详情
     * 执行用时：
     * 51 ms
     * , 在所有 Java 提交中击败了
     * 20.05%
     * 的用户
     * 内存消耗：
     * 41.4 MB
     * , 在所有 Java 提交中击败了
     * 5.09%
     * 的用户
     * @param s
     * @return
     */
    public String removeDuplicates(String s) {
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (Character character : chars) {
            if (!stack.isEmpty() && character == stack.peek()) {
                stack.pop();
            } else {
                stack.add(character);
            }
        }
        List<String> collect = stack.stream().map(String::valueOf).collect(Collectors.toList());
        String join = String.join("", collect);
        return join;
    }

    /**
     * 执行用时：
     * 13 ms
     * , 在所有 Java 提交中击败了
     * 77.82%
     * 的用户
     * 内存消耗：
     * 38.9 MB
     * , 在所有 Java 提交中击败了
     * 75.90%
     * 的用户
     * @param s
     * @return
     */
    public String removeDuplicates2(String s) {
        StringBuffer buffer = new StringBuffer();
        int index = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (index != -1 && c == buffer.charAt(index)) {
                buffer.deleteCharAt(index);
                index--;
            } else {
                buffer.append(c);
                index++;
            }
        }
        return buffer.toString();
    }
}