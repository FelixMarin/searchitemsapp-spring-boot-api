package com.searchitemsapp.aspects.logger;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomThrowableLogginAspect {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@AfterThrowing(pointcut = "execution(public String com.searchitemsapp.services.impl.ApplicationServiceImpl.listaProductosService(..))", throwing = "ex")
	public void logAfterThrowingAllMethods(Exception ex) throws Throwable {
		LOGGER.error("****LoggingAspect.logAfterThrowingAllMethods() " + ex.getLocalizedMessage(),ex);
	}
}
