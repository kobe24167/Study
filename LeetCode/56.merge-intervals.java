import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=56 lang=java
 *
 * [56] 合并区间
 *
 * https://leetcode-cn.com/problems/merge-intervals/description/
 *
 * algorithms
 * Medium (34.28%)
 * Total Accepted:    9K
 * Total Submissions: 26.1K
 * Testcase Example:  '[[1,3],[2,6],[8,10],[15,18]]'
 *
 * 给出一个区间的集合，请合并所有重叠的区间。
 * 
 * 示例 1:
 * 
 * 输入: [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * 
 * 
 * 示例 2:
 * 
 * 输入: [[1,4],[4,5]]
 * 输出: [[1,5]]
 * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
 * 
 */
/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */


class Solution2 {
    public class Interval {
        int start;
        int end;
        Interval() { 
            start = 0; 
            end = 0; 
        }
        Interval(int s, int e) { 
            start = s; 
            end = e; 
        }
    }
    public static List<Interval> merge(List<Interval> intervals) {
        LinkedList<Interval> ret = new LinkedList<>();
        Collections.sort(intervals, (a, b) -> (a.start - b.start));
        for(Interval temp : intervals) {
            if(ret.isEmpty() || ret.peekLast().end < temp.start)
                ret.add(temp);
            else
                ret.peekLast().end = Math.max(ret.peekLast().end, temp.end);
        }
        return ret; 
    }

    public static void main(String[] args) {
        List s = new ArrayList<Interval>();
        System.out.println(merge(s));
    }
}
