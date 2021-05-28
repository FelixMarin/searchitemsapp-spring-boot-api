package com.searchitemsapp.aspects.validation;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.searchitemsapp.validators.ListaProductosValidator;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Aspect
public class ServiceValidationArguments {
	
	private ListaProductosValidator validator;

	 /**
     * Valida los parámetros de entrada en la solicitud.
     *
     * @param joinPoint join point for advice
     */
    @Pointcut("execution (* com.searchitemsapp.controller.SearchItemsController.searchItems(..))")
    public void searchItems() {
    	//pointCut
    }
    
    @Before("searchItems()")
    public void validateInputAgrsInService(JoinPoint jp) {
       
    	var signature = jp.getSignature();
        MethodSignature mSignature = (MethodSignature) signature;
        Object[] args = jp.getArgs();        
        String[] stringArrayArgs = Arrays.copyOf(args, args.length, String[].class);
        var resultado = validator.isParams(stringArrayArgs, mSignature);
        
        if(!resultado) {
        	throw new IllegalArgumentException("Parámetros no validos");
        }
        
    }
}
