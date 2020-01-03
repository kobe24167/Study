import time

dateList = ['时间', 20191225.0, 20191230.0, 20191231.0]
today = int(time.strftime("%Y%m%d", time.localtime()))
amount = 3
if today in dateList:
    print(dateList.index(today))
    print('更新行')
else:
    len(dateList)
    newLine = [today, amount]
    print(len(dateList))
    print('插入行')