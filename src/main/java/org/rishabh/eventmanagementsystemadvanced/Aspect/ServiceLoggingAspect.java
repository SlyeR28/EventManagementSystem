package org.rishabh.eventmanagementsystemadvanced.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceLoggingAspect {

    // 🎯 Target all service methods
    @Pointcut("execution(* org.rishabh.eventmanagementsystemadvanced.Services.impl.*.*(..))")
    public void allServiceMethods() {}

    // 📝 Log method entry
    @Before("allServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

        StringBuilder sb = new StringBuilder("➡️ Entering " + methodName + " with params: ");
        for (int i = 0; i < args.length; i++) {
            sb.append(paramNames[i]).append("=").append(args[i]).append("; ");
        }

        log.info(sb.toString());
    }

    // ✅ Log successful exit
    @AfterReturning(pointcut = "allServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("✅ Exiting {} with result: {}", methodName, result);
    }

    // ⚠️ Log exceptions
    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("❌ Exception in {} - {}", methodName, ex.getMessage(), ex);
    }
}
