# Vultr 环境搭建遇到的问题
[Vultr](https://www.vultr.com/)
  * 首先登陆，长密码输入起来好困难
  * 启动ssh
  ```cmd
  sudo service sshd start
  ```
  * 检测ip是否可用（ping的通，但是端口不通，可能被封了）
  ```
  检测在国内端口是否open
  http://coolaf.com/tool/port
  检测在国外端口是否open
  https://www.yougetsignal.com/tools/open-ports/
  如果国外开启，国内关闭表示ip被墙了，需要换一个ip，重新创建一个虚拟机
  ```
  * 更新密码
  ```cmd
  passwd root
  ```

  * 安装netstat
  ```cmd
  yum install net-tools
  ```
  
  * [BBR加速](https://www.centos.bz/2018/01/centos-7%E5%AE%89%E8%A3%85bbr%E6%95%99%E7%A8%8B/)
  
  * 防火墙开放端口
```cmd
# 查询端口是否开放
firewall-cmd --query-port=8080/tcp
# 开放80端口
firewall-cmd --permanent --add-port=80/tcp
# 移除端口
firewall-cmd --permanent --remove-port=8080/tcp

#重启防火墙(修改配置后要重启防火墙)
firewall-cmd --reload
```