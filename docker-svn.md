# Docker中搭建SVN服务器
[DockerHub](https://hub.docker.com/r/krisdavison/svn-server)

## 话不多说，直接上命令
```
创建容器
docker run -d -p 80:80 krisdavison/svn-server:v3.0
进入容器bash
docker exec -it <container-name> bash
创建用户
htpasswd /etc/subversion/passwd ang.li
清空用户并创建
htpasswd -c /etc/subversion/passwd user-name
```
## 检查是否部署成功，浏览器访问
http://localhost/svn

## 帮助
https://help.ubuntu.com/community/Subversion

## 创建库
```
$ cd /home/svn
$ sudo mkdir myproject
$ svnadmin create /home/svn/myproject
$ chown -R www-data:subversion myproject
$ chmod -R g+rws myproject
```

## 客户端使用TortoiseSVN