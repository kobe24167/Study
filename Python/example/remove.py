import os, sys

#判断文件是否存在
def remove(filePath):
    if os.path.exists(filePath):
        os.remove(filePath)
        print("已删除！")
    else:
        print("要删除的文件不存在！")


remove('D:\\opt\\新建文件夹\\20191104\\git - 副本 (3).txt')