#
# @lc app=leetcode.cn id=57 lang=python3
#
# [57] 插入区间
#
class Solution:
    def insert(self, intervals: List[List[int]], newInterval: List[int]) -> List[List[int]]:
        intervals.append(newInterval)
        intervals = sorted(intervals)
        res = []
        n = len(intervals)
        i = 0
        while i<n:
            left = intervals[i][0]
            right = intervals[i][1]
            while i< n -1 and intervals[i+1][0] <= right:
                i+=1
                right= max(intervals[i][1], right)
            res.append([left, right])
            i+=1
        return res

