package com.cyc.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 用于记录日志的工具类，它里面提供了公共的代码
 */
@Component("logger")
@Aspect //表示当前类是一个切面类
@EnableAspectJAutoProxy  //打开注解支持
public class Logger {

    @Pointcut("execution(* com.cyc.service.impl.*.*(..))")
    public void pt1(){}

    /**
     * 用于打印日志，计划让其在切入点方法执行之前执行（切入点方法就是业务层方法）
     */
    public void printLog(){
        System.out.println("Logger类中的printLog方法开始记录日志了....");
    }

    /**
     * 前置通知
     */
    @Before("pt1()")
    public void beforeLogin(){
        System.out.println("前置通知：Logger类中的printLog方法开始记录日志了....");
    }

    /**
     * 后置通知
     */
    @AfterReturning("pt1()")
    public void afterReturningPrintLog(){
        System.out.println("后置通知：Logger类中的printLog方法开始记录日志了....");
    }

    /**
     * 异常通知
     */
    @AfterThrowing("pt1()")
    public void afterThrowingLog(){
        System.out.println("异常通知：Logger类中的printLog方法开始记录日志了....");
    }

    /**
     * 最终通知
     */
    @After("pt1()")
    public void afterLog(){
        System.out.println("最终通知：Logger类中的printLog方法开始记录日志了....");
    }

    /**
     * 环绕通知
     * 问题：
     *      当我们配置了环绕通知之后，切入点方法没有执行，而通知方法执行了。
     * 分析：
     *      通过对比动态代理中的环绕通知代码，发现动态代理的环绕通知有明确的切入点方法调用，而我们代码中没有。
     * 解决：
     *      Spring框架为我们提供了一个接口，ProceedingJoinPoint。该接口有一个方proceed()，此方法就相当于明确调用切入点方法
     *      该接口可以作为环绕通知的方法参数，在程序执行时，spring框架会为我们提供该接口的实现类供我们使用。
     *
     *  Spring中的环绕通知：
     *         它是Spring框架为我们提供的一种可以在代码中手动控制增强方法何时执行的方式（这是除在xml中手动配置增强方法的另一种形式）
     */
    @Around("pt1()")
    public Object aroundPrintLog(ProceedingJoinPoint pjp){
        Object proceed = null;
        try{
            //得到方法执行所需的参数
            Object[] args = pjp.getArgs();
            System.out.println("通知Logger类中的afterPrintLog方法开始记录日志了....前置");
            //明确调用业务层方法（切入点方法）
            proceed = pjp.proceed(args);
            System.out.println("通知Logger类中的afterPrintLog方法开始记录日志了....后置");
            return proceed;
        }catch (Throwable t){
            System.out.println("通知Logger类中的afterPrintLog方法开始记录日志了....异常");
            throw new RuntimeException(t);
        }finally {
            System.out.println("通知Logger类中的afterPrintLog方法开始记录日志了....最终");
        }
    }
}
