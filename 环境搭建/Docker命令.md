```
启动docker服务
systemctl start docker
进入容器
docker exec -it 69d1 bash
安装vi
apt-get update
apt-get install vim

下载镜像并运行
docker pull spellrun/tensorflow2-cpu
docker run -t -i spellrun/tensorflow2-cpu

复制文件到容器
docker cp MNIST_data/ 07c0388b2909:/MNIST_data

docker network
docker network ls
docker network create cluster --subnet=192.168.0.0/16
docker network inspect cluster
docker network rm cluster
```
