#
# @lc app=leetcode id=225 lang=python3
#
# [225] Implement Stack using Queues
#
class MyStack:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.qIn = []
        self.qOut = []

    def push(self, x: int) -> None:
        """
        Push element x onto stack.
        """
        self.qIn.append(x)

    def pop(self) -> int:
        """
        Removes the element on top of the stack and returns that element.
        """
        if len(self.qIn) > 0:
            while len(self.qIn) > 0:
                if len(self.qIn) == 1:
                    temp = self.qIn.pop(0)
                    self.qIn = self.qOut
                    self.qOut = []
                    return temp
                self.qOut.append(self.qIn.pop(0))

    def top(self) -> int:
        """
        Get the top element.
        """
        if len(self.qIn) > 0:
            while len(self.qIn) > 0:
                if len(self.qIn) == 1:
                    temp = self.qIn[0]
                self.qOut.append(self.qIn.pop(0))
            self.qIn = self.qOut
            self.qOut = []
            return temp

    def empty(self) -> bool:
        """
        Returns whether the stack is empty.
        """
        return len(self.qIn) == 0

