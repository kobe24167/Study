#  AWS 前期准备工作
##  步骤如下
  * 一张信用卡
  * 一个新的AWS账号
  * 创建ubuntu实例，因为AWS自己的系统不太会用，例子少
  * 创建key.pem，使用这个key去登录，首先要使用PuTTYgen转成key.ppk，然后使用它登录
```Java
  ssh -i "key1.pem" ubuntu@ec2-13-231-244-205.ap-northeast-1.compute.amazonaws.com
```
  * 使用ROOT登录的方法
  * 创建root的密码，输入如下命令：
```Java
  sudo passwd root
  ```
  * 然后会提示你输入new password。输入一个你要设置的root的密码，需要你再输入一遍进行验证。
  * 接下来，切换到root身份，输入如下命令：
  ```Java
  su root
  ```
  * 这时候就可以用root用户来操作了，如果想用root+密码登录，就继续。
  * 安装ssh
  ```Java
  ```
  sudo apt-get install openssh-server
  * 然后确认sshserver是否启动了：
  ```Java
  ps -e |grep ssh
  ```
  * 如果看到sshd那说明ssh-server已经启动了。
  * 如果没有则可以这样启动：
  ```Java
  sudo /etc/init.d/ssh start
  ```
  然后就可以使用root通过ssh登录了
  