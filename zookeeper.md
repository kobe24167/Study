# zookeeper

## 官网下载
## 官网文档
## 配置单个
## 配置集群
## 基本概念
## zk 命令
```java
1. 启动ZK服务:		./zkServer.sh start
2. 查看ZK服务状态: 	./zkServer.sh status
3. 停止ZK服务:		./zkServer.sh stop
4. 重启ZK服务:		./zkServer.sh restart
```
## 遇到的问题
	不能启动，查看log中的out日志，发现8080端口被占用
	Zookeeper AdminServer，默认使用8080端口
	可以修改在zoo.cfg中修改AdminServer的端口：
	admin.serverPort=2182
## Eclipse插件安装
	http://www.massedynamic.org/eclipse/updates/
	[参考文章](https://blog.csdn.net/yelllowcong/article/details/78230026)
## Install Docker Compose
	https://docs.docker.com/compose/install/
	