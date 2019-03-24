package net.codingme.box.office;

import java.util.Stack;

/**
 * <p>
 * 剑指office 反转链表
 *
 * @Author niujinpeng
 * @Date 2019/3/22 14:13
 */
public class Lab5_ConvertLinkedList {

    public static void main(String[] args) {
        Linked linked = new Linked(1, new Linked(2, new Linked(3, new Linked(4, null))));
        solution(linked);
    }

    /**
     * 倒序输出链表 遍历链表入栈，然后出栈。
     * 
     * @param linked
     */
    public static void solution(Linked linked) {
        Stack<Integer> stack = new Stack<>();
        while (linked != null) {
            stack.push(linked.getData());
            linked = linked.getNext();
        }
        while (stack.size() != 0) {
            System.out.println(stack.pop());
        }
    }
}

class Linked {
    private int data;
    private Linked next;

    public Linked(int data, Linked next) {
        this.data = data;
        this.next = next;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Linked getNext() {
        return next;
    }

    public void setNext(Linked next) {
        this.next = next;
    }
}