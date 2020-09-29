package com.searchitemsapp.validators.impl;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.searchitemsapp.resources.Constants;
import com.searchitemsapp.validators.ListaProductosValidator;

@Component
public class ListaProductosValidatorImpl implements ListaProductosValidator {
	
	public void isParams(String[] args,  MethodSignature mSignature) throws IllegalArgumentException {
		
		IllegalArgumentException exception = new IllegalArgumentException(mSignature.getParameterNames().toString());
		
		if(args.length == 5 && isOrdenacion(args) && isEmpresa(args)) {
			
			Arrays.asList(args).stream().forEach(value -> {
				
				if(value.length() < 1 || value.length() > 20 ||
						StringUtils.isBlank(value)) {
					throw exception;
				}
				
				value = Pattern.compile(Constants.REGEX_SPECIAL_CHARACTERS.getValue()).matcher(value).find()?StringUtils.EMPTY:value;
				value = Pattern.compile(Constants.REGEX_WORDS.getValue()).matcher(value.toLowerCase()).find()?StringUtils.EMPTY:value;
				
				if(StringUtils.isBlank(value)) {
					throw exception;
				}
			});
		}
	}
	
	private boolean isOrdenacion(String[] args) throws IllegalArgumentException {
		return NumberUtils.isDigits(args[2]) &&
				Pattern.compile(Constants.REGEX_ORDENACION.getValue()).matcher(args[2]).find();
	}
	
	private boolean isEmpresa(String[] args) throws IllegalArgumentException {
		return Pattern.compile(Constants.REGEX_EMPRESAS.getValue()).matcher(args[4]).find() ||
				Pattern.compile(Constants.REGEX_ALL.getValue()).matcher(args[4]).find();
	}
	
}
