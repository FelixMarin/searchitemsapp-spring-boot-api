package com.searchitemsapp.validators.impl;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.searchitemsapp.validators.ListaProductosValidator;

@Component
public class ListaProductosValidatorImpl implements ListaProductosValidator {
	
	private static final String REGEX_SPECIAL = "[\\'|\\?|\\*|\\<|\\>|\\[|\\]|\\{|\\}|"
			+ "\\.|\\/|\\\\|\\(|\\)|\\$|\\%|\\&|\\=|\\¿|\\@|\\\"|\\!|\\º|"
			+ "\\ª|\\||\\#|\\_|\\+|\\-|\\·|\\¡||\\;|]";
	
	private static final String REGEX_WORDS = 
			"\\b(\\w*java\\w*)|"
			+ "((\\w*import\\w*))|"
			+ "((\\w*input\\w*))|"
			+ "((\\w*function\\w*))|"
			+ "((\\w*echo\\w*))|"
			+ "((\\w*select\\w*))|"
			+ "((\\w*delete\\w*))|"
			+ "((\\w*update\\w*))|"
			+ "((\\w*alter\\w*))|"
			+ "((\\w*drop\\w*))|"
			+ "((\\w*null\\w*))|"
			+ "((\\w*from\\w*))|"
			+ "((\\w*where\\w*))|"
			+ "((\\w*text\\w*))|"
			+ "((\\w*eval\\w*))\\b";
	
	private static final String REGEX_EMPRESAS = 
			"\\b(\\w*101\\w*)|(\\w*102\\w*)"
			+ "|(\\w*103\\w*)|(\\w*104\\w*)"
			+ "|(\\w*105\\w*)|(\\w*106\\w*)"
			+ "|(\\w*107\\w*)|(\\w*108\\w*)"
			+ "|(\\w*109\\w*)|(\\w*110\\w*)"
			+ "|(\\w*111\\w*)|(\\w*112\\w*)"
			+ "|(\\w*113\\w*)|(\\w*114\\w*)"
			+ "|(\\w*115\\w*)|(\\w*,\\w*)\\b";
	
	private static final String REGEX_ORDENACION = "(\\b(1)|(2)\\b)";
	private static final String REGEX_ALL = "(\\b(\\w*ALL\\w*)\\b)";
	
	public void isParams(String[] args,  MethodSignature mSignature) throws IllegalArgumentException {
		
		IllegalArgumentException exception = new IllegalArgumentException(mSignature.getParameterNames().toString());
		
		if(args.length == 5 && isOrdenacion(args) && isEmpresa(args)) {
			
			Arrays.asList(args).stream().forEach(value -> {
				
				if(value.length() < 1 || value.length() > 20 ||
						StringUtils.isBlank(value)) {
					throw exception;
				}
				
				value = Pattern.compile(REGEX_SPECIAL).matcher(value).find()?StringUtils.EMPTY:value;
				value = Pattern.compile(REGEX_WORDS).matcher(value.toLowerCase()).find()?StringUtils.EMPTY:value;
				
				if(StringUtils.isBlank(value)) {
					throw exception;
				}
				
			});
		}
	}
	
	private boolean isOrdenacion(String[] args) throws IllegalArgumentException {
		return NumberUtils.isDigits(args[2]) &&
				Pattern.compile(REGEX_ORDENACION).matcher(args[2]).find();
	}
	
	private boolean isEmpresa(String[] args) throws IllegalArgumentException {
		return Pattern.compile(REGEX_EMPRESAS).matcher(args[4]).find() ||
				Pattern.compile(REGEX_ALL).matcher(args[4]).find();
	}
	
}
