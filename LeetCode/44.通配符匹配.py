#
# @lc app=leetcode.cn id=44 lang=python3
#
# [44] 通配符匹配
#
class Solution:
    def isMatch(self, s: str, p: str) -> bool:
        ns = len(s)
        np = len(p)
        
        i = 0
        j = 0
        afterStarJ = -1
        
        while i < ns:
            # 两字母相等，或者j为问号，双指针后移
            if j < np and (s[i] == p[j] or p[j] == '?'):
                i += 1
                j += 1
            # 出现星号，j后移，i不变，记录i、j位置
            elif j < np and p[j] == '*':
                j += 1
                afterStarJ = j
                afterStarI = i
            # 出现不匹配，回溯：j回溯到星号后，
            # i回溯到上次尝试的的下一个位置（让星号多匹配一位）
            elif afterStarJ != -1:
                afterStarI += 1
                i = afterStarI
                j = afterStarJ
            else:
                return False
        
        # s匹配完后，剩余的p都为*或者空，才算匹配
        return set(p[j:]) - set('*') == set()
