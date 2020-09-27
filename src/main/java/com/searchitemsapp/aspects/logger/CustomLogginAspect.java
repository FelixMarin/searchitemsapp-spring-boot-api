package com.searchitemsapp.aspects.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomLogginAspect {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Before("execution(public String com.searchitemsapp.services.impl.SearchItemsServiceImpl.orderedByPriceProducts(..))")
    public void logMethodAcceptionEntityAnnotatedBean(JoinPoint joinPoint) {
        LOGGER.debug("****LoggingAspect.logAroundAllMethods() : " + joinPoint.getSignature().getName() + ": After Method Execution");
    }
}