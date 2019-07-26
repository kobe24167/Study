#
# @lc app=leetcode id=232 lang=python3
#
# [232] Implement Queue using Stacks
#
class MyQueue:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.stackIn = []
        self.stackOut = []

    def push(self, x: int) -> None:
        """
        Push element x to the back of queue.
        """
        self.stackIn.append(x)

    def pop(self) -> int:
        """
        Removes the element from in front of queue and returns that element.
        """
        if len(self.stackOut) == 0:
            while len(self.stackIn) > 0:
                self.stackOut.append(self.stackIn.pop())
        return self.stackOut.pop()
        

    def peek(self) -> int:
        """
        Get the front element.
        """
        if len(self.stackOut) == 0:
            while len(self.stackIn) > 0:
                self.stackOut.append(self.stackIn.pop())
        return self.stackOut[len(self.stackOut)-1]

    def empty(self) -> bool:
        """
        Returns whether the queue is empty.
        """
        return len(self.stackOut) == 0 and len(self.stackIn) == 0
