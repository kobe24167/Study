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

查看某个key序列化后的长度
192.168.0.112:6379> debug object myhash
Value at:0x7f005c6920a0 refcount:1 encoding:ziplist serializedlength:36 lru:3341677 lru_seconds_idle:2

输出项说明：
Value at：key的内存地址
refcount：引用次数
encoding：编码类型
serializedlength：序列化长度
lru_seconds_idle：空闲时间
```

```
daemonize no //这个是是否在后台启动不占用一个主程窗口
protected-mode  yes  //能否外网访问，bind 127.0.0.1改为 #bind 127.0.0.1
pidfile /var/run/redis_6379.pid  //存放pid
requirepass password  //密码
```


info clients可以查看当前的redis连接数。

config get maxclients 可以查询redis允许的最大连接数。