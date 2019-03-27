# [Kafka](http://kafka.apache.org/)

## 外网访问需要配置
修改kafka的advertised.listeners配置即可：
```java
#listeners=PLAINTEXT://:9092
advertised.listeners=PLAINTEXT://卡夫卡部署IP:9092
zookeeper.connect=localhost:2181
```
## 常用命令
```java
启动
./kafka-server-start.sh /opt/kafka/config/server.properties
停止
./kafka-server-stop.sh
为kafka创建Topic,topic 名为test
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
查看topic信息
./kafka-topics.sh --zookeeper localhost:2181 --topic "test" --describe
测试kafka
./kafka-console-producer.sh --broker-list localhost:9092 --topic test
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

```

## [Kafka Manager in docker](https://hub.docker.com/r/hlebalbau/kafka-manager)
```
docker run -d -p 9000:9000 -e ZK_HOSTS="116.196.97.97:2181" hlebalbau/kafka-manager:stable -Dpidfile.path=/dev/null
```
## [Kafka Manager](https://github.com/yahoo/kafka-manager)
```
wget https://github.com/yahoo/kafka-manager/archive/1.3.3.23.zip
tar -zxvf kafka-manager-1.3.3.23.tar.gz
cd kafka-manager
curl https://bintray.com/sbt/rpm/rpm > bintray-sbt-rpm.repo
sudo mv bintray-sbt-rpm.repo /etc/yum.repos.d/
sudo yum install sbt
sbt -version
```