package net.codingme.box.algorithm.leetcode;

/**
 * <p>
 * 您将获得两个非空链表，表示两个非负整数。数字以相反的顺序存储，
 * 每个节点包含一个数字。添加两个数字并将其作为链接列表返回。 您
 * 可以假设这两个数字不包含任何前导零，除了数字0本身。
 * <p>
 * Example:
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 *
 * @Author niujinpeng
 * @Date 2018/11/1 16:56
 */
public class L002_AddTwoNumbers {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l11 = new ListNode(4);
        ListNode l111 = new ListNode(3);
        l11.next = l111;
        l1.next = l11;

        ListNode l2 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l222 = new ListNode(4);
        l22.next = l222;
        l2.next = l22;

        ListNode listRes = new L002_AddTwoNumbers().addTwoNumbers2(l1, l2);
        while (listRes != null) {
            System.out.print(listRes.val);
            listRes = listRes.next;
        }

    }

    /**
     * 算数解法
     * 像小学算术一样去解决这个问题，
     * isCarry用于记录溢出位
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode listRes = null;
        ListNode listTemp = null;
        boolean isCarry = false;
        while (l1 != null || l2 != null) {
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int value = n1 + n2;
            if (isCarry) {
                value++;
                isCarry = false;
            }
            if (value > 9) {
                isCarry = true;
                value = value % 10;
            }
            ListNode listNode = new ListNode(value);
            if (listRes == null) {
                listRes = listNode;
            } else {
                listTemp.next = listNode;
            }
            listTemp = listNode;
            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }
        if (isCarry) {
            listTemp.next = new ListNode(1);
        }
        return listRes;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}