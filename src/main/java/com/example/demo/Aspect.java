package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

@org.aspectj.lang.annotation.Aspect
@Slf4j
@Component
public class Aspect {
    @Around("execution(*com.example.demo..*(..))")
    public Object logAround(ProceedingJoinPoint pjg) throws Throwable{
        String name = pjg.getSignature().getName();
        log.info("BEFORE-> Method: {}",name);
        try {
            Object result = pjg.proceed();
            log.info("AFTER RETURNING-> Method: {}, Result: {}",name,result);
            return result;
        }catch (Exception ex){
            log.info("AFTER THROWING-> Method: {},Exception {}", name ,ex.getMessage());
            throw ex;
        }finally {
            log.info("AFTER -> Method: {}",name);
        }

    }

}
