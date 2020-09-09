package net.codingme.offer;

import java.util.LinkedList;
import java.util.Stack;

/**
 * <p>
 * 链表的逆序输出
 *
 * @author niujinpeng
 * @Date 2020/6/24 16:28
 */
public class LinkedReversalPrint {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        // 逆序输出
        Stack<Integer> stack = new Stack<>();
        for (Integer integer : list) {
            stack.push(integer);
        }
        while (!stack.empty()) {
            System.out.println(stack.pop());
        }
    }

}
