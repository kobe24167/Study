#-*- coding: UTF-8 -*- 
text = []
for line in open("./bug2.txt"):
    if (line.lstrip()):
        text.append(line.replace('安全漏洞','').replace('漏洞','').replace('错误','').replace('问题','').lstrip())
with open("./bug4.txt", 'a+') as f:
    f.write(''.join(text))
    