# Spring注解的原理

## Spring注解的原理使用的是Java原注解，Java提供的Annotation注解机制  
写注解时，@interface  
可用在很多地方，不影响原代码执行，是接口方法。无参数无异常抛出  
Java标准注解：  
Override重载了  
Deprecated过时的方法  
SuppressWarinings禁止警告  

## Java注解mate-annotation  
注解注解的注解  
### @Retention: 定义注解的保留策略  
@Retention(RetentionPolicy.SOURCE)   //注解仅存在于源码中，在class字节码文件中不包含  
@Retention(RetentionPolicy.CLASS)     // 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，  
@Retention(RetentionPolicy.RUNTIME)  // 注解会在class字节码文件中存在，在运行时可以通过反射获取到  
首先要明确生命周期长度 SOURCE < CLASS < RUNTIME ，所以前者能作用的地方后者一定也能作用。一般如果需要在运行时去动态获取注解信息，那只能用 RUNTIME 注解；如果要在编译时进行一些预处理操作，比如生成一些辅助代码（如 ButterKnife），就用 CLASS注解；如果只是做一些检查性的操作，比如 @Override 和 @SuppressWarnings，则可选用 SOURCE 注解。  

### @Target：定义注解的作用目标  
源码为： 
@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.ANNOTATION_TYPE)  
public @interface Target {  
    ElementType[] value();    
}    
@Target(ElementType.TYPE)   //接口、类、枚举、注解  
@Target(ElementType.FIELD) //字段、枚举的常量  
@Target(ElementType.METHOD) //方法  
@Target(ElementType.PARAMETER) //方法参数  
@Target(ElementType.CONSTRUCTOR)  //构造函数  
@Target(ElementType.LOCAL_VARIABLE)//局部变量  
@Target(ElementType.ANNOTATION_TYPE)//注解  
@Target(ElementType.PACKAGE) ///包  
  
### @Document：说明该注解将被包含在javadoc中
### @Inherited：说明子类可以继承父类中的该注解
  
## Java自定义注解例子
### 新建一个自定义注解：
```Java
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target({ElementType.FIELD,ElementType.METHOD})
@interface MyAnno{
    public String name() default "zhangsan";
    public String email() default "hello@example.com";
}
```
### 定义一个User类，来使用自定义注解：
```Java
class  User{

    @MyAnno(name = "zhang")
    private String name;

    @MyAnno(name = "zhang@example.com")
    private String email;


    @MyAnno(name = "sayHelloWorld")
    public String sayHello(){
        return "";
    }
}
```
### 通过反射获取注解信息：
```Java
Method[] methods = User.class.getMethods();
//Field[] fields = User.class.getFields();
Field[] fields = User.class.getDeclaredFields();
/*
    getFields：只能访问public属性及继承到的public属性
    getDeclaredFields：只能访问到本类的属性，不能访问继承到的属性

    getMethods：只能访问public方法及继承到的public方法
    getDeclaredMethods：只能访问到本类的方法，不能访问继承到的方法

    getConstructors：只能访问public构造函数及继承到的public构造函数
    getDeclaredConstructors：只能访问到本类的构造函数，不能访问继承到的构造函数
*/

for (Field field : fields) {
    MyAnno annotation = field.getAnnotation(MyAnno.class);
    if(annotation!=null){
        System.out.println("property="+annotation.name());
    }
}
for (Method method : methods) {
    MyAnno annotation = method.getAnnotation(MyAnno.class);
    if(annotation!=null){
        System.out.println("sayHello="+annotation.name());
    }
}
```
## 注解元素的默认值
注解元素必须有确定的值，要么制定定义时指定，要么使用注解时指定
```Java
	public int id() default -1;
```

原文地址：https://blog.csdn.net/bluuusea/article/details/79996572

## 注解处理器
定义了注解，必须有配套的注解处理器，通常都是通过Class对象配合反射机制来处理；网上和各种教科书中很多例子。
https://race604.com/annotation-processing/  
http://ifeve.com/java-8-features-tutorial/  
http://www.infoq.com/cn/articles/Type-Annotations-in-Java-8  
