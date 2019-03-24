# 尝试在docker中搭建MySql集群
失败了

docker network create cluster --subnet=192.168.0.0/16
docker run -d --net=cluster --name=management1 --ip=192.168.0.2 mysql/mysql-cluster ndb_mgmd
docker run -d --net=cluster --name=ndb1 --ip=192.168.0.3 mysql/mysql-cluster ndbd
docker run -d --net=cluster --name=ndb2 --ip=192.168.0.4 mysql/mysql-cluster ndbd
docker run -d --net=cluster --name=mysql1 -p 3306:3306 --ip=192.168.0.10 -e MYSQL_RANDOM_ROOT_PASSWORD=true mysql/mysql-cluster mysqld


docker logs mysql1 2>&1 | grep PASSWORD
docker exec -it mysql1 mysql -uroot -p
ALTER USER 'root'@'localhost' IDENTIFIED BY 'Wicnor2008';

update user set host = ’%’ where user = ’root’;
grant all privileges  on *.* to root@'%' identified by "Wicnor2008";