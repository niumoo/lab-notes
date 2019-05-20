package net.codingme.box.structure.tree;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * <p>
 * 二叉树的遍历测试
 *
 * @Author niujinpeng
 * @Date 2019/4/10 10:33
 */
@Data
@Slf4j
public class BitTree<T> {
    private BitTree root;
    private T value;
    private BitTree left;
    private BitTree right;

    public BitTree(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public BitTree(T value, BitTree left, BitTree right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    /**
     * 前序遍历
     * 
     * @param rootTree
     */
    public void preRrder(BitTree rootTree) {
        if (rootTree != null) {
            System.out.println(rootTree.value);
            preRrder(rootTree.left);
            preRrder(rootTree.right);
        }
    }

    /**
     * 非递归前序遍历
     * 
     * @param rootTree
     */
    public void stackOrder(BitTree rootTree) {
        Stack<BitTree> stack = new Stack<BitTree>();
        while (true) {
            while (rootTree != null) {
                System.out.println(rootTree.value);
                stack.push(rootTree);
                rootTree = rootTree.left;
            }
            if (stack.isEmpty()) {
                break;
            }
            BitTree bitTree = stack.pop();
            rootTree = bitTree.right;
        }
    }

    public static void main(String[] args) {
        BitTree<Integer> rootTree = new BitTree<>(0);
        rootTree.setLeft(new BitTree(1, new BitTree(2), new BitTree(3)));
        rootTree.setRight(new BitTree(4, new BitTree(5), new BitTree(6)));
        rootTree.setRoot(rootTree);
        // 遍历
        rootTree.preRrder(rootTree.root);
        System.out.println("---------------");
        rootTree.stackOrder(rootTree.root);
    }

}
