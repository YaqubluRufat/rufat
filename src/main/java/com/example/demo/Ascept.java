package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class Ascept {

    @Around("execution(* com.example.demo..*(..))")
    public Object logAround (ProceedingJoinPoint pjg) throws Throwable{
        String name = pjg.getSignature().getName();
        log.info("BEFORE -> Method: {}",name);
        try {
            Object result = pjg.proceed();
            log.info("AFTER RETURNING -> Method: {}, Result: {}",name,result);
            return result;
        }catch (Exception ex){
            log.info("AFTER THROWING -> Method: {}, Error: {}",name,ex.getMessage());
            return ex;
        }finally {
            log.info("AFTER -> Method: {}",name);

        }

    }
}
