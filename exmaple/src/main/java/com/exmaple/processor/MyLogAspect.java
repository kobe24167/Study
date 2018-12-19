package com.exmaple.processor;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.exmaple.annotation.Log;

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
     * @param myLog
     * @return
     */
    @Around(value = "pointcut() && @annotation(myLog)")
    public Object around(ProceedingJoinPoint point, Log myLog) {

        System.out.println("++++ִ����around����++++");

        String requestUrl = myLog.name();

        //���ص�����
        Class clazz = point.getTarget().getClass();
        //���صķ���
        Method method = ((MethodSignature) point.getSignature()).getMethod();

        System.out.println("ִ���� ��:" + clazz + " ����:" + method + " �Զ��������ַ:" + requestUrl);

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
     * @param myLog
     * @param result
     * @return
     */
    @AfterReturning(value = "pointcut() && @annotation(myLog)", returning = "result")
    public Object afterReturning(JoinPoint joinPoint, Log myLog, Object result) {

//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpSession session = request.getSession();

        System.out.println("++++ִ����afterReturning����++++");

        System.out.println("ִ�н����" + result);

        return result;
    }

    /**
     * ����ִ�к� ���׳��쳣
     *
     * @param joinPoint
     * @param myLog
     * @param ex
     */
    @AfterThrowing(value = "pointcut() && @annotation(myLog)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Log myLog, Exception ex) {
        System.out.println("++++ִ����afterThrowing����++++");
        System.out.println("����" + myLog.name() + " �����쳣");
    }

}