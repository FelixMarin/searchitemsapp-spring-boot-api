package com.searchitemsapp.aspects.validation;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
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
public class CustomArgsServiceValidation {
	
	private ListaProductosValidator validator;

    @Pointcut("execution (public String com.searchitemsapp.controller.SearchItemsController.searchItems(..))")
    public void listaProductosController() {
    }
    
    @Before("execution (public String com.searchitemsapp.controller.SearchItemsController.searchItems(..))")
    public void validateInputAgrsInService(JoinPoint jp) throws IllegalArgumentException {
       
    	Signature signature = jp.getSignature();
        MethodSignature mSignature = (MethodSignature) signature;
        Object[] args = jp.getArgs();        
        String[] stringArrayArgs = Arrays.copyOf(args, args.length, String[].class);

        try {
        	
        	validator.isParams(stringArrayArgs, mSignature);
    		
        } catch (IllegalArgumentException exception) {
            throw exception;
        }
    }
}
