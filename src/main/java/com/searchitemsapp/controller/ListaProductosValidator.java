package com.searchitemsapp.controller;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

@Component
public class ListaProductosValidator {
	
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
	
	private static final String REGEX_TRES_CIFRAS = "^10[1-9]|1[1-9]\\d";
	private static final String REGEX_ORDENACION = "(\\b(1)|(2)\\b)";
	private static final String REGEX_ALL = "(\\b(\\w*ALL\\w*)\\b)";
	
	public boolean isParams(@NotNull final String... input) {
		
		if(input.length != 5) {
			return Boolean.FALSE;
		}
		
		for (String value : input) {
			
			if(value.length() < 1 || value.length() > 20 ||
					StringUtils.isBlank(value)) {
				return Boolean.FALSE;
			}
			
			value = Pattern.compile(REGEX_SPECIAL).matcher(value).find()?StringUtils.EMPTY:value;
			value = Pattern.compile(REGEX_WORDS).matcher(value.toLowerCase()).find()?StringUtils.EMPTY:value;
			
			if(StringUtils.isBlank(value)) {
				return Boolean.FALSE;
			}
		}
			
		return Boolean.TRUE;
	}
	
	public boolean isNumeric(@NotNull final String... input) {
		
		for (String value : input) {
			if(!NumberUtils.isDigits(value) ||
					!Pattern.compile(REGEX_TRES_CIFRAS).matcher(value).find()) {
				return Boolean.FALSE;
			}
		}
		
		return Boolean.TRUE;
	}
	
	public boolean isOrdenacion(@NotNull String ordenacion) {
		return NumberUtils.isDigits(ordenacion) &&
				Pattern.compile(REGEX_ORDENACION).matcher(ordenacion).find();
	}
	
	public boolean isEmpresa(String empresas) {
		return Pattern.compile(REGEX_EMPRESAS).matcher(empresas).find() ||
				Pattern.compile(REGEX_ALL).matcher(empresas).find();
	}
	
}
