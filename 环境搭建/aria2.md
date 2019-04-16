# aria2 [https://aria2.github.io/](https://aria2.github.io/)
[Manual](https://aria2.github.io/manual/en/html/index.html)

## 下载命令
```
.\aria2c http://example.org/mylinux.iso
.\aria2c 'magnet:?xt=urn:btih:248D0A1CD08284299DE78D5C1ED359BB46717D8C'
```
## 启动RPC
```
.\aria2c --enable-rpc --rpc-allow-origin-all
```
配置Linux
https://www.cnblogs.com/zhuxiaoxi/p/7714457.html
https://moderras.github.io/2017/09/aria2%E7%9A%84rpc%E8%B0%83%E7%94%A8%E6%96%B9%E6%B3%95/

GUI使用Chrome插件YAAW
配置JSON-RPC Path：http://localhost:6800/jsonrpc

问题来了，已经下载文件到服务器，那么
尝试下载文件到本地？
支持断点续传？
使用zookeeper？