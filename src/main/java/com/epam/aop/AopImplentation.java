package com.epam.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Aspect
@Component
public class AopImplentation {
	
   
    @Pointcut("execution(* com.epam.restapi.*.*(..))")
	public void loggingPointCut() {}
    @Before("loggingPointCut()")
    public void before(JoinPoint joinPoint) {
    	log.info("Before method invoked::"+joinPoint.getSignature());
    }
    @After("loggingPointCut()")
    public void after(JoinPoint joinPoint) {
    	log.info("after method invoked::"+joinPoint.getSignature());
    }
}
