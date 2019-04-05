# Json转换对象，内容里包含Date的问题

  * 使用Gson，转换成Json发送到@RequestBody后Date格式化不对，需要使用统一的Json工具
  * 是否该使用Date也是一个问题，数据库应存储的long的时间，但是展示使用的是String，所以需要权衡一下，或是数据库model转成展示的model
  * 暂时使用SpringBoot 自带的Json包Jackson
```Java
import com.fasterxml.jackson.databind.ObjectMapper;

String json = new ObjectMapper().writeValueAsString(obj);
```