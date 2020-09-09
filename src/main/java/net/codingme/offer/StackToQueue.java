package net.codingme.offer;

import java.util.Stack;

/**
 * <p>
 * 使用两个栈栈实现一个队列
 *
 * @author niujinpeng
 * @Date 2020/6/24 16:40
 */
public class StackToQueue {

    public static void main(String[] args) {
        appendTail(1);
        System.out.println(deleteHead());
        appendTail(2);
        appendTail(3);
        System.out.println(deleteHead());
        appendTail(4);
        System.out.println(deleteHead());
        System.out.println(deleteHead());
    }

    static Stack<Integer> stack1 = new Stack<>();
    static Stack<Integer> stack2 = new Stack<>();

    public static void appendTail(Integer num) {
        while (!stack2.empty()) {
            stack1.push(stack2.pop());
        }
        stack1.push(num);
    }

    public static Integer deleteHead() {
        while (!stack1.empty()) {
            stack2.push(stack1.pop());
        }
        if (stack2.empty()) {
            return null;
        }
        return stack2.pop();
    }

}
