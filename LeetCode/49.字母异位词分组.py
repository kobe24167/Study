#
# @lc app=leetcode.cn id=49 lang=python3
#
# [49] 字母异位词分组
#
class Solution:
    def groupAnagrams(self, strs: List[str]) -> List[List[str]]:
        res = {}
        for i in strs:
            a = [c for c in i]
            a.sort()
            key = tuple(a)
            value = res.get(key)
            if value:
                value.append(i)
            else:
                res.update({key:[i]})
        return res.values()

