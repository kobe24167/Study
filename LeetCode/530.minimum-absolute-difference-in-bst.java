/*
 * @lc app=leetcode.cn id=530 lang=java
 *
 * [530] 二叉搜索树的最小绝对差
 *
 * https://leetcode-cn.com/problems/minimum-absolute-difference-in-bst/description/
 *
 * algorithms
 * Easy (51.65%)
 * Total Accepted:    2.5K
 * Total Submissions: 4.8K
 * Testcase Example:  '[1,null,3,2]'
 *
 * 给定一个所有节点为非负值的二叉搜索树，求树中任意两节点的差的绝对值的最小值。
 * 
 * 示例 :
 * 
 * 
 * 输入:
 * 
 * ⁠  1
 * ⁠   \
 * ⁠    3
 * ⁠   /
 * ⁠  2
 * 
 * 输出:
 * 1
 * 
 * 解释:
 * 最小绝对差为1，其中 2 和 1 的差的绝对值为 1（或者 2 和 3）。
 * 
 * 
 * 注意: 树中至少有2个节点。
 * 
 */
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode(int x) { val = x; } }
 */
class Solution {
    static public int getMinimumDifference(TreeNode root) {
        preOrderTraverse(root);
        return min;
    }

    static TreeNode root;
    static int min = Integer.MAX_VALUE;

    static public void add(int value) {
        TreeNode newNode = new TreeNode(value);
        if (root == null) {
            root = newNode;
        } else {
            TreeNode temp = root;
            while (true) {
                if (value < temp.val) { // 小的放左边
                    if (temp.left == null) {
                        if (temp.val - value < min) {
                            min = temp.val - value;
                        }
                        temp.left = newNode;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else { // 大的放右边
                    if (temp.right == null) {
                        if (value - temp.val < min) {
                            min = value - temp.val;
                        }
                        temp.right = newNode;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
        }
    }

    static void preOrderTraverse(TreeNode t) {
        if (null == t || t.val == 0) {
            return;
        }
        if (t.left != null) {
            preOrderTraverse(t.left); // 中序遍历左子树
        }
        if (t.val != 0) {
            add(t.val);
        }

        if (t.right != null) {
            preOrderTraverse(t.right); // 最后遍历右子树
        }
    }

    static public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    
    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(5);
        n1.right = n2;
        n2.left = new TreeNode(3);
        System.out.println(getMinimumDifference(n1));
    }
}
