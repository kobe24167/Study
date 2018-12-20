#  SpringMVC到Spring Boot过程和心得
##  步骤如下
  * 配置依赖maven
  * 建立spring boot启动
  * 配置application.properties
  * log4j，slf4j版本更新
  * 升级spring版本
  * maven指定Tomcat版本<tomcat.version>8.0.42</tomcat.version>
  * 配置ServletWebServerFactory
  * el包问题，去掉了javaEE的包
  * 配置mybatis xml位置
  * 删除工程中同名的类
  * 调整日志级别，打印sql
  * 打包成jar
  * 安装maven及使用打包命令
  * 打包配置resources文件进入jar包
  * 打包配置删除resources下的class及mybatis xml
  * 配置jar包下拦截器
  * 删除拦截器、和filter，改用Spring tag
  * 调整login页面路径
  * 打包后页面找不到