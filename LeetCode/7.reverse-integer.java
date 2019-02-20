/*
 * @lc app=leetcode.cn id=7 lang=java
 *
 * [7] 整数反转
 *
 * https://leetcode-cn.com/problems/reverse-integer/description/
 *
 * algorithms
 * Easy (31.39%)
 * Total Accepted:    78.6K
 * Total Submissions: 250.5K
 * Testcase Example:  '123'
 *
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * 
 * 示例 1:
 * 
 * 输入: 123
 * 输出: 321
 * 
 * 
 * 示例 2:
 * 
 * 输入: -123
 * 输出: -321
 * 
 * 
 * 示例 3:
 * 
 * 输入: 120
 * 输出: 21
 * 
 * 
 * 注意:
 * 
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 * 
 */
class Solution1 {
    public static int reverse(int x) {
        if (x == 0) {
            return 0;
        }
        Integer a = new Integer(x);
        StringBuilder sb = new StringBuilder();
        for (int i = a.toString().length()-1; i>=0; i--) {
            sb.append(a.toString().charAt(i));
        }
        if(sb.toString().indexOf("-") > -1) {
            sb.deleteCharAt(sb.length()-1);
            sb.insert(0, "-");
        }
        try {
            return new Integer(sb.toString()).intValue();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(reverse(1534236469));
    }
}
