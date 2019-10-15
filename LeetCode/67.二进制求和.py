#
# @lc app=leetcode.cn id=67 lang=python3
#
# [67] 二进制求和
#

# @lc code=start
class Solution:
    def addBinary(self, a: str, b: str) -> str:
        index = 1
        alen = len(a)
        blen = len(b)
        c = []
        f = 0
        while alen>=index or blen>=index:
            ab = 0
            bb = 0
            if alen>=index:
                ab = int(a[-index])
            if blen>=index:
                bb = int(b[-index])
            n = ab+bb+f
            if n == 2:
                f = 1
                n = 0
            elif n == 3:
                f = 1
                n = 1
            else:
                f = 0
            c+=str(n)
            # c.insert(0, n)
            index += 1
            
        else:
            if f == 1:
                c+="1"
        r = ""
        i = 1
        while i<=len(c) :
            r+=c[-i]
            i+=1

        return r


        
# @lc code=end

