package org.rishabh.eventmanagementsystemadvanced.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PaymentLoggingAspect {

    // Pointcut for all methods inside PaymentService and PaymentController
    @Pointcut("execution(* org.rishabh.eventmanagementsystemadvanced.Services.impl.PaymentServiceImpl.*(..)) || " +
            "execution(* org.rishabh.eventmanagementsystemadvanced.Controllers.PaymentController.*(..))")
    public void paymentLayer() {}

    // Before executing any payment method
    @Before("paymentLayer()")
    public void logBefore(JoinPoint joinPoint) {
        log.info(" [START] Executing: {} with arguments: {}",
                joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    // After successful execution
    @AfterReturning(pointcut = "paymentLayer()", returning = "result")
    public void logAfterSuccess(JoinPoint joinPoint, Object result) {
        log.info(" [SUCCESS] {} executed successfully. Result: {}",
                joinPoint.getSignature().toShortString(), result);
    }

    // After throwing exception
    @AfterThrowing(pointcut = "paymentLayer()", throwing = "ex")
    public void logAfterError(JoinPoint joinPoint, Exception ex) {
        log.error(" [ERROR] Exception in {}: {}",
                joinPoint.getSignature().toShortString(), ex.getMessage(), ex);
    }

    // After method execution (no matter what)
    @After("paymentLayer()")
    public void logAfterFinally(JoinPoint joinPoint) {
        log.info("🏁 [END] Completed execution of {}", joinPoint.getSignature().toShortString());
    }
}
