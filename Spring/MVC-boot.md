#  SpringMVC到Spring Boot过程和心得
##  步骤如下
  * 配置依赖maven
	加入或升级依赖包：spring boot,mysql,mybatis,aspectj,log4j,json,zxing,jasperreports,pinyin4j,jsp,tomcat
  * 建立spring boot启动
```Java
	Application.java
```
  * 配置application.properties
	支持jsp
```Java
	spring.thymeleaf.cache=false
	spring.thymeleaf.enabled=false
	spring.mvc.view.prefix=/WEB-INF/pages/
	spring.mvc.view.suffix=.jsp
```
  * log4j，slf4j版本更新
  * 升级spring版本
  * maven指定Tomcat版本<tomcat.version>8.0.42</tomcat.version>
  ```Java
  <properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<!-- spring版本号 -->
		<spring.version>5.1.3.RELEASE</spring.version>
		<mybatis.version>3.4.6</mybatis.version>
		<!-- log4j日志文件管理包版本 -->
		<log4j.version>1.2.17</log4j.version>
		<tomcat.version>8.5.35</tomcat.version>
	</properties>
  ```
  * 配置ServletWebServerFactory
  * el包问题，去掉了javaEE的包
  * 配置mybatis xml位置
  ```Java
	mybatis.typeAliasesPackage=com.test
	mybatis.mapperLocations=classpath:**/mapping/*.xml
	mybatis.configuration.log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  ```
  * 删除工程中同名的类
  * 调整日志级别，打印sql
  ```Java
	logging.level.com.test.*.mapping=DEBUG
  ```
  * 打包成jar
  * 安装maven及使用打包命令
  ```Java
  mvn clean package
  ```
  * 打包配置resources文件进入jar包
  * 打包配置删除resources下的class及mybatis xml
  * 调整login页面路径
  * 打包后页面找不到问题解决
  ```Java
  <build>
		<finalName>RehabManage</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<configuration>
					<useSystemClassLoader>false</useSystemClassLoader>
					<packagingExcludes>WEB-INF/lib/slf4j-api-1.5.6.jar</packagingExcludes>
				</configuration>
			</plugin>
		</plugins>
		<resources>
			<resource>
				<directory>src/main/resources</directory>
				<includes>
					<include>**/*</include>
				</includes>
			</resource>
			<resource>
				<directory>src/main/webapp</directory>
				<includes>
					<include>**/*.xml</include>
				</includes>
			</resource>
			<resource>
				<directory>src/main/java</directory>
				<includes>
					<include>**/*.properties</include>
					<include>**/*.xml</include>
				</includes>
				<!-- 是否替换资源中的属性 -->
				<filtering>false</filtering>
			</resource>
		</resources>
	</build>
  ```

  * 删除拦截器、和filter，改用Spring tag
  * 配置新的拦截器

```Java
import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		if (o instanceof HandlerMethod) {
			// 检查是否有@LoginRequired登录注解
			HandlerMethod handlerMethod = (HandlerMethod) o;
			Method method = handlerMethod.getMethod();
			LoginRequired annotation = method.getAnnotation(LoginRequired.class);
			// 没有直接放行，有的话进行相应的验证
			if (annotation != null) {
				// 具体验证逻辑略
			}
		}
		String currentURL = httpServletRequest.getRequestURI();
		HttpSession session = httpServletRequest.getSession(false);
		Cookie[] cs = httpServletRequest.getCookies();

		if (session == null || session.getAttribute("onlineUser") == null) {
			if (cs != null) {
				for (Cookie c : cs) {
					if (c.getName().equals("autoLogin")) {// 如果存在自动登录的cookie
						String value = c.getValue();// 用户名称
						httpServletRequest.getSession().setAttribute("onlineUser", value);
					}
					break;
				}
			}
			if (!currentURL.contains("/pages")) {
				return true;
			}
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {

	}
}
```
  
  * 配置新的拦截器拦截请求的页面

```Java
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@GetMapping("/")
	public String login(Map<String, Object> model) {
		return "login";
	}

	@RequestMapping("/foo")
	public String foo(Map<String, Object> model) {
		throw new RuntimeException("Foo");
	}
	
	@GetMapping("/pages/{path}")
	public String pages(@PathVariable("path") String path) {
		return path;
	}
	
	@GetMapping("/pages/mainCatalogue/{path}")
	public String mainCatalogue(@PathVariable("path") String path) {
		return "mainCatalogue/"+path;
	}

}
```
  * dao加@Mapper，不然找不到接口
  ```Java
	@Mapper
	public interface PatientAppointmentDao {
  ```
  * maven项目编译后没有生成target/class文件
  * 设置Spring boot启动端口
  ```Java
  server.port=8080
  ```
  * 同时两个SpringBoot项目放在Tomcat下启动，第二个项目会报unable to register MBean
  ```Java
  解决方法：
  在SpringBoot项目中配置文件加上
  spring.jmx.enabled=false
  ```