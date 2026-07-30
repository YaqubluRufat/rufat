import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Aspect {

    @Around("execution(* com.example.marketsercive.Service..*(..))")
    public Object logAround (ProceedingJoinPoint pjg) throws Throwable{
        String name = pjg.getSignature().getName();
        Object[] args = pjg.getArgs();
        log.info("BEFORE -> Method: {}, Args: {}", name,args);
        long time = System.currentTimeMillis();
        try {
            Object result = pjg.proceed();
            long times = System.currentTimeMillis() - time;
            log.info("After: Method: {}, Args: {}, Result: {}, Times: {}",name,args,result,times);
            return result;
        }catch (Exception ex){
            long times = System.currentTimeMillis() - time;
            log.info("AFTER THROWING -> Method: {}, Error: {}, Times: {}",name,ex.getMessage(),times);
            throw ex;
        }finally {
            log.info("AFTER-> Method: {}",name);

        }


}}
