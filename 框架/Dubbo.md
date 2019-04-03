# Dubbo学习

因为我做的Dubbo启动依赖Spring Boot, 帮我做了很多自动配置，
为了避免Spring Boot的影响
首先需要去除启动端口，这样就可以启动N个Dubbo服务了
```Java
    public static void main(String[] args) {
    	new SpringApplicationBuilder(Ms5Application.class)
    		.web(WebApplicationType.NONE)
    		.run(args);
    }
```
在启动参数中增加下面命令
```
-Ddubbo.application.logger=slf4j
```