package com.example.demo.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(public * com.example.demo.feature..*Controller.*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethod(joinPoint, "Controller");
    }

    @Around("execution(public * com.example.demo.feature..*Service.*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethod(joinPoint, "Service");
    }

    private Object logMethod(ProceedingJoinPoint joinPoint, String layer) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String location = className + "#" + methodName;

        logger.info("[{}] Start - {}", layer, location);
        try {
            Object result = joinPoint.proceed();
            logger.info("[{}] End - {} - Completed Successfully", layer, location);
            return result;
        } catch (Exception e) {
            logger.info("[{}] End - {} - Failed with exception: {}", layer, location, e.getMessage());
            throw e;
        }
    }
}
