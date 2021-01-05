package com.duck.code.commons.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @program: codeduck-blog-serve
 * @description: 日志切面
 * @author: Code Duck
 * @create: 2020-12-17 14:33
 */

/**
 * 切面 = 通知 + 切点
 * 切点：定义哪些类的哪些方法需要拦截
 * 通知：定义拦截方法后要做什么
 */
@Component  // 将对象交给IOC容器管理
@Aspect // 声明当前类是一个切面
@Slf4j
public class LogAspect {

    /**
     * 定义切点： @Pointcut(“ 匹配表达式 ”)
     * 匹配表达式实际上是指定哪些类的方法需要被拦截
     * <p>
     * AOP 切点表达式概述
     * 1. 执行任意公共方法： execution(public *(..))
     * 2. 执行任意的set方法： execution(* set*(..))
     * 3. 执行com.xxx.service包下的任意类的任意方法：
     * execution(* com.xxx.service.*.*(..))
     * 4. 执行com.xxx.service包 及其 子包 下的任意类的任意方法：
     * execution(* com.xxx.service..*.*(..))
     * <p>
     * 注：表达式中第一个 * 代表方法的修饰范围，
     * 可选值有：private protected public (* 代表所有范围)
     */
//    @Pointcut("execution(* com.duck.code.*.service.*.*(..))")
    @Pointcut("@annotation(com.duck.code.commons.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    /**
     * 前置通知：将通知应用到定义的切点
     * 目标类方法执行前 执行该通知
     */
    @Before(value = "pointcut()")
    public void doBefore() {
        System.out.println("前置通知...");
    }

    /**
     * 目标类方法（无异常）执行后，执行后置通知
     */
    @AfterReturning(value = "pointcut()")
    public void doAfterReturn() {
        System.out.println("后置通知...");
    }

    /**
     * 目标类方法（无异常或有异常）执行后，执行最终通知
     */
    @After(value = "pointcut()")
    public void doAfter() {
        System.out.println("最终通知...");
    }

    /**
     * 目标类方法发生异常，执行异常通知
     */
    @AfterThrowing(value = "pointcut()")
    public void doThrow() {
        System.out.println("异常通知...");
    }


    /**
     * 方法执行前后 通过环绕通知定义响应处理
     * 需要通过显示调用对应的方法，否则无法访问指定方法 joinPoint.proceed()
     *
     * @param joinPoint:连接点
     * @throws Throwable
     */
    @Around(value = "pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {

        System.out.println("前置通知...");

        Object object = null;
        try {
            object = joinPoint.proceed();
            System.out.println("后置通知... =>" + joinPoint.getTarget());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("异常通知...");
        } finally {
            System.err.println("最终通知...");
        }
        return object;
    }


}
