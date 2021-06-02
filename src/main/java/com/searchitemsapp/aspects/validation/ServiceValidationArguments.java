package com.searchitemsapp.aspects.validation;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.searchitemsapp.validators.ListaProductosValidator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
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
    
    @Pointcut("execution (* com.searchitemsapp.controller.LiveSearchController.liveSearchProduct(..))")
    public void liveSearchProduct() {
    	//pointCut
    }
    
    @Before("searchItems()")
    public void validateInputAgrsInService(JoinPoint jp) {
    	
    	var stringArrayArgs = getArgs(jp);
 
        if(!validator.isParams(stringArrayArgs)) {
        	throw new IllegalArgumentException("No valid params: " + Arrays.toString(stringArrayArgs));
        }
    }
    
    @Before("liveSearchProduct()")
    public void validateInputLiveSearch(JoinPoint jp) {
    	
    	var agrs = Arrays.asList(getArgs(jp));    	
    	var filteredArgs = agrs.stream().filter(StringUtils::isNotBlank).findFirst();    	
    	var param = filteredArgs.filter(StringUtils::isNotBlank).orElse(StringUtils.EMPTY);    	
        var resultado = validator.isParamsLiveSearch(param); 
        
        if(!resultado) {
        	throw new IllegalArgumentException("No valid params: " + param);
        }
    }
    
    private String[] getArgs(JoinPoint jp) {
    	var signature = jp.getSignature();
        MethodSignature mSignature = (MethodSignature) signature;
        Object[] args = jp.getArgs();
        String[] stringArrayArgs = Arrays.copyOf(args, args.length, String[].class);
        log.error(Arrays.toString(mSignature.getParameterNames()) + " valor de entrada: " + Arrays.toString(args));
        return stringArrayArgs;
    }
}
