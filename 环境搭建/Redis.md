# Redis 知识及命令
## Installation
```
$ wget http://download.redis.io/releases/redis-5.0.4.tar.gz
$ tar xzf redis-5.0.4.tar.gz
$ cd redis-5.0.4
$ make
```
```
The binaries that are now compiled are available in the src directory. Run Redis with:
$ src/redis-server /opt/redis/redis.conf
You can interact with Redis using the built-in client:
$ src/redis-cli
redis> set foo bar
OK
redis> get foo
"bar"
关闭(无密码)
./redis-cli shurdown
关闭（有密码）
./redis-cli
auth "password"
shurdown
```

```
daemonize no //这个是是否在后台启动不占用一个主程窗口
protected-mode  yes  //能否外网访问，bind 127.0.0.1改为 #bind 127.0.0.1
pidfile /var/run/redis_6379.pid  //存放pid
requirepass password  //密码
```


info clients可以查看当前的redis连接数。

config get maxclients 可以查询redis允许的最大连接数。