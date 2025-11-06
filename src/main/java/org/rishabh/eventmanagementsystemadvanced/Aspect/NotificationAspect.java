package org.rishabh.eventmanagementsystemadvanced.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class NotificationAspect {

    @Pointcut("execution(* org.rishabh.eventmanagementsystemadvanced.Services.impl.NotificationService.sendNotification(..))")
    public void notificationMethods() {}

    @Before("notificationMethods()")
    public void beforeNotification(JoinPoint joinPoint) {
        log.info("📨 Sending notification: {}", joinPoint.getArgs()[0]);
    }

    @AfterReturning("notificationMethods()")
    public void afterNotification() {
        log.info("✅ Notification sent successfully!");
    }

    @AfterThrowing(pointcut = "notificationMethods()", throwing = "ex")
    public void onNotificationError(Throwable ex) {
        log.error("🚨 Notification sending failed: {}", ex.getMessage());
    }
}
