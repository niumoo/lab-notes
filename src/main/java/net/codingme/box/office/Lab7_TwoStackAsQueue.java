package net.codingme.box.office;

import java.util.Stack;

/**
 * <p>
 * 两个栈实现一个队列
 *
 * @Author niujinpeng
 * @Date 2019/3/22 14:32
 */
public class Lab7_TwoStackAsQueue {
    public static void main(String[] args) {
        Queue queue = new Queue();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        queue.push(4);
        while (true) {
            System.out.println(queue.pop());
        }

    }

    public static class Queue {
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();

        public void push(int node) {
            stack1.push(node);
        }

        public int pop() {
            if (stack1.isEmpty() && stack2.isEmpty()) {
                throw new RuntimeException("queue is empty");
            }
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.pop();
        }
    }
}
