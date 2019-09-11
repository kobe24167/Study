#
# @lc app=leetcode.cn id=60 lang=python3
#
# [60] 第k个排列
#

class Solution:
    def getPermutation(self, n: int, k: int) -> str:
        if n == 0:
            return []
        nums = [i + 1 for i in range(n)]
        used = [False for _ in range(n)]
        factorial = [1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880]
        return self.__dfs(nums, used, n, k, 0, [], factorial)

    def __dfs(self, nums, used, n, k, depth, pre, factorial):
        if depth == n:
            # 在叶子结点处结算
            return ''.join(pre)
        # 后面的数的全排列的个数
        ps = factorial[n - 1 - depth]
        for i in range(n):
            # 如果这个数用过，就不再考虑
            if used[i]:
                continue
            # 后面的数的全排列的个数小于 k 的时候，执行剪枝操作
            if ps < k:
                k -= ps
                continue
            pre.append(str(nums[i]))
            # 因为直接走到叶子结点，因此状态不用恢复
            used[i] = True
            return self.__dfs(nums, used, n, k, depth + 1, pre, factorial)

