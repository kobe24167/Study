# Spring Boot 上传文件大小限制的问题

Spring Boot版本为2.1.1
application.properties增加
```Java
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=100MB
spring.http.multipart.enabled=false
```
nginx配置nginx.conf文件，修改或增加大小限制
```Java
http {
	client_max_body_size 50m;
	}
```