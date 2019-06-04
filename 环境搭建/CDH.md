  # Hadoop集成环境CDH
安装到docker中,但是这样是单机，要搭建集群
```
docker run \
 -id \
 --hostname=quickstart.cloudera \
 --privileged=true  \
 -p 8020:8020 -p 7180:8080 -p 21050:21050 -p 50070:50070 -p 50075:50075 \
 -p 50010:50010 -p 50020:50020 -p 8890:8890 -p 60010:60010 -p 10002:10002  \
 -p 25010:25010 -p 25020:25020 -p 18088:18088 -p 8088:8088 -p 19888:19888 \
 -p 7187:7187 -p 11000:11000 -t -p 8888:80 \
 --net host --name=mycdh \
 cloudera/quickstart /usr/bin/docker-quickstart
 
 #进入到镜像中，启动cloudera-manager
 docker exec -it mycdh bash
 ./home/cloudera/cloudera-manager --enterprise
 
 ```
 
