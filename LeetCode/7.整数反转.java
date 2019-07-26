/*
 * @lc app=leetcode.cn id=7 lang=java
 *
 * [7] 整数反转
 */
class Solution {
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

