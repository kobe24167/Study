# -*- coding: utf-8 -*-
"""
获得线程, 进程 ID,NAME
总结:
 
"""
import threading
import psutil
import os
import datetime
# 1 获取线程ID,NAME
t = threading.currentThread()
#线程ID
print('Thread id : %d' % t.ident)
#线程NAME
print('Thread name : %s' % t.getName())
 
# 2 获取线程ID,NAME
pid = os.getpid()
p = psutil.Process(pid)
print('----------------')
#进程ID
print('Process id : %d' % pid)
#进程NAME
print('Process name : %s' % p.name())
#获取进程bin路径
print('Process bin  path : %s' % p.exe())
#获取pid对应的路径
print('Process path : %s' % p.cwd())
#进程状态
print('Process status : %s' % p.status())
#进程运行时间
print('Process creation time : %s' % datetime.datetime.fromtimestamp(p.create_time()).strftime("%Y-%m-%d %H:%M:%S"))
#CPU使用情况
print(p.cpu_times())
#内存使用情况
print('Memory usage : %s%%' % p.memory_percent())
#硬盘读取信息
print(p.io_counters())
#打开进程socket的namedutples列表
print(p.connections())
#此进程的线程数
print('Process number of threads : %s' % p.num_threads())
 