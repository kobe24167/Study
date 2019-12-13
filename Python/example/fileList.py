import os
import time

#10秒检测一次
#检测60分钟（10秒*360次）
for i in range(0,360):
    downloadFinished = True
    for filename in os.listdir(r'D:\\opt\\新建文件夹\\20191104'):
        if filename.endswith('.tmp'):
            print (filename)
            downloadFinished = False
    if downloadFinished:
        break
    time.sleep(10)
print (downloadFinished)