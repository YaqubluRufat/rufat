package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@Aspect
public class AspectClass {

    @Around("execution(* com.example.demo..*(..))")
    public Object logAround(ProceedingJoinPoint pjg)throws Throwable {
        String name = pjg.getSignature().getName();
        Object[] args = pjg.getArgs();
        log.info("BEFORE-> Method: {},Args: {}", name, args);
        long start = System.currentTimeMillis();
        try {
            Object result = pjg.proceed();
            long time = System.currentTimeMillis() - start;
            log.info("AFTER RETURNING: Method: {},Result: {},Args: {},Time: {}", name, result,args, time);
            return result;
        } catch (Exception ex) {
            long time = System.currentTimeMillis() - start;
            log.info("AFTER THROWING -> Method: {}, Error: {}, time: {}", name, ex.getMessage(), time);
            throw ex;
        } finally {
            log.info("AFTER -> Method: {}", name);

        }
    }
}
