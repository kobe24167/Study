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
@Aspect //AOP ����
@Component
public class MyLogAspect {


    //�����
    @Pointcut(value = "@annotation(com.exmaple.annotation.Log)")
    private void pointcut() {

    }


    /**
     * �ڷ���ִ��ǰ��
     *
     * @param point
     * @param log
     * @return
     */
    @Around(value = "pointcut() && @annotation(log)")
    public Object around(ProceedingJoinPoint point, Log log) {

        System.out.println("++++ִ����around����++++");

        String requestUrl = log.name();

        //���ص�����
        Class clazz = point.getTarget().getClass();
        //���صķ���
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        System.out.println("ִ���� ��:" + clazz + " ����:" + method + " �Զ��������ַ:" + requestUrl);
        System.out.println("����:" + new Gson().toJson(point.getArgs()));

        try {
            return point.proceed(); //ִ�г���
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable.getMessage();
        }
    }

    /**
     * ����ִ�к�
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

        System.out.println("++++ִ����afterReturning����++++");

        System.out.println("ִ�н����" + new Gson().toJson(result));

        return result;
    }

    /**
     * ����ִ�к� ���׳��쳣
     *
     * @param joinPoint
     * @param log
     * @param ex
     */
    @AfterThrowing(value = "pointcut() && @annotation(log)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Log log, Exception ex) {
        System.out.println("++++ִ����afterThrowing����++++");
        System.out.println("����" + log.name() + " �����쳣");
    }

}