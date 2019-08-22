#
# @lc app=leetcode.cn id=40 lang=python3
#
# [40] 组合总和 II
#
class Solution:
    def combinationSum2(self, candidates: List[int], target: int) -> List[List[int]]:
        candidates.sort()
        path = []
        result = []
        def df(self, candidates, target, path, start, size):
            if target == 0:
                try:
                    if result.index(path[:]):
                        pass
                except ValueError as e:
                    result.append(path[:])
            for i in range(start, size):
                r = target - candidates[i]
                if r < 0:
                    break
                path.append(candidates[i])
                start = 1+i
                df(self, candidates, r, path, start, size)
                path.pop()
        df(self, candidates, target, path, 0, len(candidates))
        return result
