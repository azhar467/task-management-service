package com.azhar.taskmanagement.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.azhar.taskmanagement.controller..*(..))") // Adjust the package to match your controllers
    public void controllerMethods() {}

    @After("controllerMethods()")
    public void logMethodDetails(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String methodType = joinPoint.getSignature().getDeclaringTypeName();
        String threadName = Thread.currentThread().getName();

        log.info("Invoked on Thread: " + threadName +
                           ", Method Type: " + methodType + 
                           ", Method Name: " + methodName);
    }
}
