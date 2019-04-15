# 记录常用的，搭建环境用到的Linux命令

## 解压包
```
tar -zxvf XXXX.tar.gz
```
## 查看端口
```
lsof -i:8000
netstat -tunlp |grep 端口号
```
## Linux分析进程占用内存最高和占用CPU最高
```
查看占用内存最高的5个进程
ps aux | sort -k4nr | head -n 5
查看占用cpu最高的5个进程
ps aux | sort -k3nr | head -n 5
查看所有信息使用命令
ps aux
```
## 环境变量
```
修改profile文件
vim /etc/profile
使其生效
source /etc/profile
```
## pip安装
```
yum -y install epel-release
yum -y install python-pip
pip install --upgrade pip
pip -V
```
## 升级Python, 安装在自己定义的目录有问题，以下的安装路径不行
```
python -V
yum install gcc openssl-devel bzip2-devel libffi-devel
wget https://www.python.org/ftp/python/3.7.2/Python-3.7.2.tgz
tar xzf Python-3.7.2.tgz
cd Python-3.7.2
安装编译
./configure --enable-optimizations
make altinstall
rm /usr/src/Python-3.7.2.tgz
python3.7 -V
```
### 将原来 python 的软链接重命名：
将 python 链接至 python3.7：
```
mv /usr/bin/python /usr/bin/python.bak
ln -s /usr/bin/python3.7 /usr/bin/python
python -V
```
### 配置 yum
升级 Python 之后，由于将默认的 python 指向了 python3.7，yum 不能正常使用，需要编辑 yum 的配置文件：
```
vi /usr/bin/yum
vi /usr/libexec/urlgrabber-ext-down
将 #!/usr/bin/python 改为 #!/usr/bin/python3.7，保存退出即可。
```
## Tomcat启动
```
/opt/apache-tomcat-8.5.38/bin/shartup.sh
```
## Nginx
```
/etc/nginx/nginx.conf
http://nginx.org/en/docs/beginners_guide.html
nginx -s stop
nginx -s quit 
nginx -s reload 
nginx -s reopen 
```
## zk 命令
```
启动ZK服务:		./zkServer.sh start
查看ZK服务状态: ./zkServer.sh status
停止ZK服务:		./zkServer.sh stop
重启ZK服务:		./zkServer.sh restart
待补充 zkCli
```
## 使用docker stack deploy或docker-compose部署集群ZooKeeper
```
创建目录/opt/docker-deploy
创建文件(stack.yml)[https://github.com/kobe24167/Study/blob/master/stack.yml]
命令：docker stack deploy -c stack.yml zookeeper
或：docker-compose -f stack.yml up
关闭：docker-compose down
```
## kafka
```
启动		./kafka-server-start.sh /opt/kafka/config/server.properties
停止 		./kafka-server-stop.sh
创建topic 	./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
展示topic 	./kafka-topics.sh --list --zookeeper localhost:2181
描述topic 	./kafka-topics.sh --describe --zookeeper localhost:2181 --topic test
生产者：	./kafka-console-producer.sh --broker-list localhost:9092 --topic test
消费者：	./kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginnin
```
## zipkin命令
```
安装 curl -sSL https://zipkin.io/quickstart.sh | bash -s
启动 java -jar zipkin.jar
带mysql的启动
STORAGE_TYPE=mysql MYSQL_HOST=116.196.97.97 MYSQL_TCP_PORT=3306 MYSQL_DB=zipkin MYSQL_USER=root MYSQL_PASS=Wincor2008 nohup java -jar zipkin.jar &
```
## 安装docker
[参考文章](https://blog.csdn.net/achenyuan/article/details/80195401#linuxdockerCompose_388)

## 安装docker-compose
### 官方步骤
```
sudo curl -L "https://github.com/docker/compose/releases/download/1.23.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```
### 使用pip安装
```
pip install -U docker-compose
docker-compose -version
docker-compose -h
```

## docker stack命令
```
根据compose文件或bundle文件创建stack
docker stack deploy --compose-file docker-compose.yml vossibility
docker stack deploy --bundle-file vossibility-stack.dab vossibility
列出所有stack		docker stack ls
列出stack中所有任务	docker stack ps
删除stack 			docker stack rm
列出stack中所有服务	docker stack services stack-name
```