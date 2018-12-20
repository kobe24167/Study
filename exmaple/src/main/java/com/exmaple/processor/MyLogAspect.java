package com.exmaple.processor;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.exmaple.annotation.Log;
import com.google.gson.Gson;

/**
 * @author sam
 * @since 2017/7/13
 */
@Aspect //AOP 切面
@Component
public class MyLogAspect {


    //切入点
    @Pointcut(value = "@annotation(com.exmaple.annotation.Log)")
    private void pointcut() {

    }


    /**
     * 在方法执行前后
     *
     * @param point
     * @param log
     * @return
     */
    @Around(value = "pointcut() && @annotation(log)")
    public Object around(ProceedingJoinPoint point, Log log) {

        System.out.println("++++执行了around方法++++");

        String requestUrl = log.name();

        //拦截的类名
        Class clazz = point.getTarget().getClass();
        //拦截的方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        System.out.println("执行了 类:" + clazz + " 方法:" + method + " 自定义请求地址:" + requestUrl);
        System.out.println("参数:" + new Gson().toJson(point.getArgs()));

        try {
            return point.proceed(); //执行程序
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable.getMessage();
        }
    }

    /**
     * 方法执行后
     *
     * @param joinPoint
     * @param log
     * @param result
     * @return
     */
    @AfterReturning(value = "pointcut() && @annotation(log)", returning = "result")
    public Object afterReturning(JoinPoint joinPoint, Log log, Object result) {

//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpSession session = request.getSession();

        System.out.println("++++执行了afterReturning方法++++");

        System.out.println("执行结果：" + new Gson().toJson(result));

        return result;
    }

    /**
     * 方法执行后 并抛出异常
     *
     * @param joinPoint
     * @param log
     * @param ex
     */
    @AfterThrowing(value = "pointcut() && @annotation(log)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Log log, Exception ex) {
        System.out.println("++++执行了afterThrowing方法++++");
        System.out.println("请求：" + log.name() + " 出现异常");
    }

}