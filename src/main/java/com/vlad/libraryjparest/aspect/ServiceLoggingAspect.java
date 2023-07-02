package com.vlad.libraryjparest.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Component
@Aspect
public class ServiceLoggingAspect {

    Logger log = Logger.getLogger("Service layer");
    FileHandler fileHandler;
    String loggingFile = "service.log";

    public ServiceLoggingAspect() {
        try {
            fileHandler = new FileHandler(loggingFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.addHandler(fileHandler);
        fileHandler.setFormatter(new SimpleFormatter());
    }


    @Pointcut("execution(* com.vlad.libraryjparest.service.*.*(..))")
    private void serviceMethods(){
    }

    @Around("serviceMethods()")
    public Object aroundAllServiceMethods(
            ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        log.info("Begin of " + methodName + " method");
        log.info("Arguments: " + Arrays.toString(joinPoint.getArgs()));
        Object targetMethodResult = joinPoint.proceed();
        log.info("End of " + methodName + " method");
        log.info("Method return value: " + targetMethodResult);
        return targetMethodResult;
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void afterThrowingServiceMethods(Throwable exception){
        log.info("An exception was thrown: " + exception.getMessage());
    }
}
