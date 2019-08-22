#
# @lc app=leetcode.cn id=39 lang=python3
#
# [39] 组合总和
#
class Solution:
    def combinationSum(self, candidates: List[int], target: int) -> List[List[int]]:
        candidates.sort()
        path = []
        result = []
        def df(self, candidates, target, path, start, size):
            if target == 0:
                result.append(path[:])
            for i in range(start, size):
                r = target - candidates[i]
                if r < 0:
                    break
                path.append(candidates[i])
                df(self, candidates, r, path, i, size)
                path.pop()
        df(self, candidates, target, path, 0, len(candidates))
        return result
