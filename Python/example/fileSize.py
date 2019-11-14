def sizeFormat(size, is_disk=False, precision=2):
    '''
    size format for human.
        byte      ---- (B)
        kilobyte  ---- (KB)
        megabyte  ---- (MB)
        gigabyte  ---- (GB)
        terabyte  ---- (TB)
        petabyte  ---- (PB)
        exabyte   ---- (EB)
        zettabyte ---- (ZB)
        yottabyte ---- (YB)
    '''
    formats = ['KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
    unit = 1000.0 if is_disk else 1024.0
    if not(isinstance(size, float) or isinstance(size, int)):
        raise TypeError('a float number or an integer number is required!')
    if size < 0:
        raise ValueError('number must be non-negative')
    if size < 1000:
        return str(size)+'B'
    for i in formats:
        size /= unit
        if size < unit:
            return f'{round(size, precision)}{i}'
    return f'{round(size, precision)}{i}'

import os
import time
dirName = time.strftime("%Y%m%d", time.localtime())
path = 'D:\\opt\\新建文件夹\\'+dirName
path = 'D:\\opt\\新建文件夹\\20191104'
for filename in os.listdir(path):
    print(sizeFormat(os.path.getsize(os.path.join(path, filename))))

