# Redis 知识及命令
## Installation
$ wget http://download.redis.io/releases/redis-5.0.4.tar.gz
$ tar xzf redis-5.0.4.tar.gz
$ cd redis-5.0.4
$ make

The binaries that are now compiled are available in the src directory. Run Redis with:
$ src/redis-server
You can interact with Redis using the built-in client:
$ src/redis-cli
redis> set foo bar
OK
redis> get foo
"bar"


info clients可以查看当前的redis连接数。

config get maxclients 可以查询redis允许的最大连接数。