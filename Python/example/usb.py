
from time import sleep
from shutil import copytree
from psutil import disk_partitions
 
while True:
    #  设置休眠时间
    sleep(1)
    #  检测所有的驱动器，进行遍历寻找哦
    for item in disk_partitions():
        if 'removable' in item.opts:
            driver, opts = item.device, item.opts
            #  输出可移动驱动器符号
            print('发现usb驱动：', driver)
            break
        #  没有找到可输出驱动器
        else:
            print('没有找到可移动驱动器')
            continue
    break