# zookeeper

## 官网下载
## 官网文档
## 配置单个
## 配置集群
## 基本概念
## 遇到的问题
	不能启动，查看log中的out日志，发现8080端口被占用
	Zookeeper AdminServer，默认使用8080端口
	可以修改在zoo.cfg中修改AdminServer的端口：
	admin.serverPort=8888