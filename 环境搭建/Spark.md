  # Spark学习
  也许在学习Spark之前应学习的是几个概念的内涵及相关性
  * 大数据（我觉得Spark应该在这个范畴，但是没有数据不行，还是要先爬些数据）
  * 机器学习
  * 模式识别
  * 数据挖掘
  * 人工智能
  ## Hadoop及Spark的安装部署，Hadoop官方的入门案例是在Ubuntu上，以后都在Ubuntu上开发
  不通版本hadoop配置不一定一样，建议到官网学习，目前不建议用框架[例子](https://blog.csdn.net/china1987427/article/details/82057250)
  ```
  cd opt
  #安装Java
  wget jdk
  tar -zxvf jdk.tgz 
  #安装Spark
  wget spark-2.4.3-bin-hadoop2.7.tgz 
  tar -zxvf spark-2.4.3-bin-hadoop2.7.tgz 
  #安装Scala注意版本（安装SBT）
  wget https://downloads.lightbend.com/scala/2.12.8/scala-2.12.8.tgz
  tar -zxvf scala-2.12.8.tgz 
  #配置环境变量
  export JAVA_HOME="/opt/jdk1.8.0_211"
  export SCALA_HOME=/opt/scala
  export SPARK_HOME=/opt/spark-2.4.3-bin-hadoop2.7
  export PATH="$PATH:$JAVA_HOME/bin"
  export PATH="$PATH:$SCALA_HOME/bin"
  export PATH="$PATH:$SPARK_HOME/bin"
  #添加Spark运行环境
  cd /usr/local/spark/spark-2.3.0-bin-hadoop2.7/conf
  sudo cp spark-env.sh.template  spark-env.sh
  sudo vim spark-env.sh
  #配置Spark服务器节点
  cp slaves.template slaves
  sudo vim slaves
  #启动Spark集群
  cd /usr/local/spark/spark-2.3.0-bin-hadoop2.7/sbin
  # 这里重命名一个，方便在外面访问
  sudo cp start-all.sh spark-start-all.sh 
  # 启动Spark集群
  spark-start-all.sh 
  访问 IP:8080
  ```
  ## hdfs命令
  ```
  hdfs dfs -ls /
  hdfs dfs -put /usr/local/spark/spark-2.3.0-bin-hadoop2.7/README.md /
  #使用cat命令查看一个HDFS中的README.md文件的内容
  hdfs dfs -cat ./README.md
  ```
  ## Scala安装及学习,sbt打包
  未成功，打包出来的class有问题
  ## [使用Java打jar包并submit](https://www.cnblogs.com/hapjin/archive/2018/11/30/10046238.html)
  ```
  ./spark-2.3.1-bin-hadoop2.7/bin/spark-submit --class net.hapjin.spark.nick.SparkNickPreClassification nick_classifier.jar
  ```
  
