import time

# 获取下载列表
index = 1
names = {}
try:
    while 1:
        #获取文件名
        name = "name"+str(index)
        names.setdefault(name, -1)
        index += 1
        if index == 20:
            break
except expression as identifier:
    pass

print(names)

time.sleep( 2 )
# 找到对应的结果行
for i in range(1, len(names)):
    names[("name"+str(i))] = i

#获取结果
def getResult(key, value):
    if key == "name2":
        return -1
    return 0

while 1:
    #等待下载结果
    notFinished = 0
    for k,v in names.items():
        if v > 0:
            notFinished += 1
            value = getResult(k, v)
            names[k] = value
    if notFinished:
        print(notFinished)
        print(names)
        time.sleep( 2 )
    else:
        break
    
#总结下载结果
success = 0
failed = 0
failedNames = []
for k,v in names.items():
    if v == 0:
        success += 1
    else:
        failedNames.append(k)
        failed += 1
print(names)
print('成功了'+str(success)+'个')
print('失败了'+str(failed)+'个')

prompt = "下载失败的文件："
if failed:
    for name in failedNames:
        prompt += name + ","
    prompt = prompt[:-1]
print(prompt)