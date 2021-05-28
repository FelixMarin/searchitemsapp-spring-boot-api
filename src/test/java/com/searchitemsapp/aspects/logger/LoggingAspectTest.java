package com.searchitemsapp.aspects.logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.controller.UserController;

@Import(AnnotationAwareAspectJAutoProxyCreator.class)
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class LoggingAspectTest {
	
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    private static LoggingAspect sampleAspect = new LoggingAspect();

    @Test
    void logAroundTest() throws Throwable {
    	sampleAspect.logAround(proceedingJoinPoint);
    	verify(proceedingJoinPoint, times(1)).proceed();
    	verify(proceedingJoinPoint, never()).proceed(null);   	
	}
       
    @BeforeAll
    static void pointcutTest() {
    	sampleAspect.springBeanPointcut();
    	sampleAspect.applicationPackagePointcut();
    	sampleAspect.productsBuilderException();
    	sampleAspect.saveUserException();
    }
    
    

}
